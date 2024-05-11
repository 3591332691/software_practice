<script setup>
	import {
	  ref,
	  onBeforeMount,
	  defineProps
	} from 'vue'
	
	const books = ref([])
	
	onBeforeMount(() => {
	  getBooks()
	})
	
	const getBooks = () => {
	  const app = getApp()
	  books.value = app.globalData.mockData.books
	  console.log(books.value);
	}
	
	// 跳转到小说详情页
	const goDetail = (e) => {
	  uni.navigateTo({
		url:'../../../pages/book-detail/index?bid=' + e.currentTarget.dataset.bid
	  })
	}
	
	function tosearch() {
		uni.navigateTo({
			url:"../../../pages/search/index"
		})
	}

</script>

<template>
	<view class="viewport">
		<view class="books">
			<view class="item" @click="tosearch">
				<image class="img" mode=""></image>
				<view class="title search">
					<image src="../../../static/assets/icon/sousuo.png" mode=""></image>
					<text>找小说</text>
				</view>
			</view>
			<view class="item" v-for="(item, index) in books" :key="item.id" :data-bid="item.id" @click="goDetail">
				<image class="img" :src="item.img" mode=""></image>
				<text class="title">{{item.title}}</text>
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
			// height: 350rpx;

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

			.search {
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