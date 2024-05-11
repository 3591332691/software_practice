<script setup>
import { ref } from 'vue';
import { onMounted } from 'vue';
	onMounted(() => {
		fetchData()
	})

	const user = ref({})
	const fetchData = async () => {
		const res = await uni.request({
			url: "http://localhost:8080/GetUserInfo?open_id=2",
			method: "GET"
		})
		user.value = res.data
		console.log(user.value);
	}

	function allInfo() {
		uni.navigateTo({
			url: './components/info'
		})
	}

	function myBook() {
		uni.navigateTo({
			url: "./components/bookshelf"
		})
	}

	function myPublish() {
		uni.navigateTo({
			url: "./components/publish"
		})
	}
</script>

<template>
	<view class="my">
		<view class="info">
			<image src="../../static/images/touxiang.jpg"></image>
			<view class="base">
				<text class="name">昵称: {{user.Name}}</text>
				<text class="uid">number</text>
			</view>
			<view class="all">
				<view @click="allInfo">
					<image class="icon" src="../../static/assets/icon/youjiantou.png" mode=""></image>
				</view>
			</view>
		</view>

		<view class="content">
			<view class="item">
				<view>
					<view @click="myBook">
						<image class="icon" src="../../static/assets/icon/shujia-L.png" mode=""></image>
						<text class="text">我的书架</text>
					</view>
				</view>
			</view>

			<view class="item">
				<view>
					<view @click="myPublish">
						<image class="icon" src="../../static/assets/icon/shu.png" mode=""></image>
						<text class="text">我的发表</text>
					</view>
				</view>
			</view>
		</view>
	</view>
</template>

<style lang="scss">
	.info {
		display: flex;
		align-items: center;
		padding: 20rpx;
		background-color: #f4f4f4;

		image {
			margin-right: 40rpx;
			width: 150rpx;
			height: 150rpx;
			border-radius: 50%;
		}

		.base {
			flex: 1;

			.name {
				display: block;
				padding: 20rpx;
				font-size: 36rpx;
			}
		}

		.all {
			padding: 5rpx;
			width: 50rpx;
			height: 50rpx;
			background-color: rgb(185, 185, 185);
			border-radius: 50%;

			.icon {
				width: 50rpx;
				height: 50rpx;
				filter: brightness(0) invert(1);
			}
		}
	}

	.content {
		padding: 0 20rpx;

		.item {
			position: relative;
			padding: 30rpx 20rpx;
			border-bottom: 1px solid #ddd;

			.icon {
				position: absolute;
				top: 30rpx;
				width: 50rpx;
				height: 50rpx;
			}

			.text {
				margin-left: 70rpx;
				font-size: 36rpx;
			}
		}
	}
</style>