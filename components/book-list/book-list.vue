<script setup>
	import {
		ref,
		defineProps
	} from 'vue'

	import {
		onShow
	} from "@dcloudio/uni-app"

	const books = ref([])

	onShow(() => {
		getBooks()
	})

	const getBooks = () => {
		books.value = []
		uni.request({
			url: "http://150.158.39.251:8080/GetAllBooks",
			method: "GET",
			success: (res) => {
				// books.value = res.data
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
							}
						})
					}
				})

			}
		})
	}

	// 跳转到小说详情页
	const goDetail = (e) => {
		uni.navigateTo({
			url: '../../pages/book-detail/index?bid=' + e.currentTarget.dataset.bid + '&&from=index'
		})
	}
</script>


<template>
	<view>
		<!-- 小说列表 -->
		<view class="item" v-for="(item,index) in books" :key="item.book_id" :data-bid="item.book_id" @click="goDetail">
			<view class="itemleft">
				<image class="img" :src="item.image"></image>
			</view>
			<view class="itemright">
				<view class="itemtitle"><text>{{item.book_name}}</text></view>
				<view class="itemtauthor"><text>作者：{{item.author}}</text></view>
				<view class="itemprofile">小说简介：{{item.brief_introduction}}</view>
				<!-- <view class="itemupdate"><text>更新日期：{{item.update}}</text></view> -->
			</view>
		</view>
	</view>
</template>


<style>
	.item {
		display: flex;
		padding: 15rpx;
		border: 1rpx solid #efefef;
		border-radius: 8rpx;
		margin: 15rpx;
		box-shadow: 1rpx 1rpx 15rpx #ddd;
	}

	.itemleft image {
		height: 400rpx;
		width: 300rpx;
		display: block;
		margin-right: 15rpx;
	}

	.itemright {
		flex: 1;
		display: flex;
		flex-direction: column;
		font-size: 30rpx;
	}

	.itemtitle {
		font-family: Arial, Helvetica, sans-serif;
		overflow: hidden;
		font-weight: bold;
		font-size: 36rpx;
	}

	.itemprofile {
		margin-top: 15rpx;
		font-size: 30rpx;
	}

	.img {
		border-radius: 10rpx;
		display: flex;
		height: 100%;
		width: auto;
	}

	.itemauthor {
		margin-top: 15rpx;
		font-size: 34rpx;
		overflow: hidden;
	}
</style>