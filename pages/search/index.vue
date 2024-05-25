<script setup>
	import {
		onMounted,
		ref
	} from 'vue';
	import {onShow} from "@dcloudio/uni-app"
	const keyWords = ref("")
	const books = ref([])
	const hasData = ref(1)
	
	const toSearch = () => {
		// console.log(keyWords);
		books.value = []
		hasData.value = 0
		if (keyWords.value.trim() === '') {
			uni.showToast({
				icon: "error",
				title: "请输入关键词"
			})
			keyWords.value = ''
			return
		}
		uni.request({
			url: "http://150.158.39.251:8080/SearchBookByName?name=" + keyWords.value,
			method: "GET",
			success: (res) => {
				// console.log(res.data);
				if (res.data === "no books found") {
					hasData.value = 0
				} else {
					const arr = res.data
					// console.log(arr);
					arr.map((item) => {
						if (item.publish) {
							uni.request({
								url: "http://150.158.39.251:8080/GetUserInfo?open_id=" + item
									.author_id,
								method: "GET",
								success: (result) => {
									item.author = result.data.Name
									books.value.push(item)
									// console.log(books.value[0]);
									hasData.value = 1
								}
							})
						}
					})
					// books.value = res.data
				}
			}
		})
	}

	const recommandBook = ref([])
	const getRecommand = () => {
		uni.request({
			url: "http://150.158.39.251:8080/GetAllBooks",
			method: "GET",
			success: (res) => {
				// console.log(res.data);
				// recommandBook.value = res.data.slice(0, 3)
				res.data.map((item) => {
					if (recommandBook.value.length <= 3) {
						if (item.publish) {
							recommandBook.value.push(item)
						}
					} else {
						return
					}
				})
			}
		})
	}
	
	onShow(() => {
		getRecommand()
	})

	// 跳转到小说详情页
	const goDetail = (e) => {
		uni.navigateTo({
			url: '../../pages/book-detail/index?bid=' + e.currentTarget.dataset.bid + '&&from=index'
		})
	}
</script>

<template>
	<view class="viewport">
		<view class="search">
			<image class="img" src="../../static/assets/icon/sousuo.png" mode="" @click="toSearch"></image>
			<input class="input" type="text" placeholder="请输入书名或作者名" v-model="keyWords" />
			<view class="txt" @click="toSearch">搜索</view>
		</view>

		<view v-if="hasData" class="result" v-for="(item,index) in books" :key="item.book_id" :data-bid="item.book_id"
			@click="goDetail">
			<view class="itemleft">
				<image class="img" :src="item.image"></image>
			</view>
			<view class="itemright">
				<view class="itemtitle"><text>{{item.book_name + "  作者  " + item.author}}</text></view>
				<view class="itemprofile"><text>小说简介：{{item.brief_introduction}}</text></view>
			</view>
		</view>
		<view v-else class="noData">
			<view>没有找到书籍</view>
		</view>

		<view v-if="books.length === 0 || hasData === 0" class="recommand">
			<view class="title">猜你想看</view>
			<view class="content" v-for="(item, index) in recommandBook" :key="item.book_id" :data-bid="item.book_id"
				@click="goDetail">
				<view class="item">
					<text class="text">{{item.book_name}}</text>
				</view>
			</view>
		</view>
	</view>
</template>



<style lang="scss">
	Page {
		height: 100%;
	}

	.search {
		display: flex;
		align-items: center;
		margin-top: 15rpx;
		border-bottom: 1rpx solid #ddd;

		.input {
			flex: 1;
			padding-left: 15rpx;
			height: 80rpx;
		}

		.img {
			width: 80rpx;
			height: 80rpx;
		}

		.txt {
			padding: 20rpx;
			font-size: 32rpx;
			color: rgb(185, 185, 185);
		}
	}

	.recommand,
	.noData {
		margin-top: 20rpx;
		padding: 20rpx;

		.title {
			font-size: 36rpx;
			margin-bottom: 20rpx;
		}

		.content {
			.item {
				padding: 10rpx;
				height: 80rpx;
				line-height: 80rpx;

				.text {
					font-size: 34rpx;
				}
			}

			.item:hover {
				background: linear-gradient(to right, #f4f4f4, #fff);
				border-radius: 15rpx;
			}
		}
	}

	.result {
		display: flex;
		padding: 15rpx;
		border: 1rpx solid #efefef;
		border-radius: 8rpx;
		margin: 15rpx;
		box-shadow: 1rpx 1rpx 15rpx #ddd;

		.itemleft image {
			padding-top: 15rpx;
			height: 100rpx;
			width: 100rpx;
			display: block;
			margin-right: 30rpx;
		}

		.itemright {
			display: flex;
			flex-direction: column;
			justify-content: space-around;
			font-size: 30rpx;
		}

		.itemtitle {
			padding-top: 5rpx;
			font-family: Arial, Helvetica, sans-serif;
			overflow: hidden;
			text-overflow: ellipsis;
			white-space: nowrap;
			font-weight: bold;
			font-size: 36rpx;
		}

		.itemprofile {
			margin-top: 10rpx;
			font-size: 32rpx;
			overflow: hidden;
			text-overflow: ellipsis;
			display: -webkit-box;
			-webkit-line-clamp: 4;
			-webkit-box-orient: vertical;
		}
	}
</style>