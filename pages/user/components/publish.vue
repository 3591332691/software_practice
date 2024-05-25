<script setup>
	import {
		ref
	} from "vue"
	import {
		onShow
	} from "@dcloudio/uni-app"

	const open_id = uni.getStorageSync("open_id")
	const books = ref([])
	const getBook = () => {
		uni.request({
			url: "http://150.158.39.251:8080/GetBooksICreated?open_id=" + open_id,
			method: "GET",
			success: (res) => {
				// console.log(res);
				if (Array.isArray(res.data)) {
					books.value = res.data
				}
			}
		})
	}

	onShow(() => {
		getBook()
	})

	const goDetail = (e) => {
		uni.navigateTo({
			url: '../../../pages/book-detail/index?bid=' + e.currentTarget.dataset.bid + '&&from=publish'
		})
	}

	function toEdit() {
		uni.navigateTo({
			url: "../../../pages/publishBook/publishBook"
		})
	}
</script>

<template>
	<view class="viewport">
		<view class="books">
			<view class="item" @click="toEdit">
				<image class="img" mode=""></image>
				<view class="title edit">
					<image src="../../../static/assets/icon/jiufuqianbaoicon06.png" mode=""></image>
					<text>去发布</text>
				</view>
			</view>

			<view class="item" v-for="(item, id) in books" :key="item.book_id" :data-bid="item.book_id"
				@click="goDetail">
				<image class="img" :src="item.image" mode=""></image>
				<text class="title">{{item.book_name}}</text>
			</view>
		</view>
	</view>
</template>

<style lang="scss">
	.viewport {
		display: flex;
		flex-direction: column;
		height: 100%;
		background-size: auto 420rpx;
	}

	.books {
		display: flex;
		flex-wrap: wrap;
		// justify-content: space-between;
		padding: 30rpx 0;

		.item {
			margin-left: 30rpx;
			margin-bottom: 20rpx;
			width: 210rpx;
			height: 350rpx;

			.img {
				height: 300rpx;
				width: 210rpx;
				border: 1px solid #61ba8b;
				border-radius: 10rpx;
			}



			.title {
				display: block;
				padding-top: 10rpx;
				line-height: 1;
				font-size: 28rpx;
				color: #4a4a4a;
				text-align: center;
			}

			&:first-child {
				position: relative;
			}

			.edit {
				position: absolute;
				top: 40%;
				left: 50%;
				transform: translate(-50%, -50%);
				font-size: 26rpx;

				image {
					display: block;
					margin: 0 auto;
					width: 50rpx;
					height: 50rpx;
				}
			}
		}
	}
</style>