<script setup>
	import {
		ref,
		defineProps,
		onMounted
	} from 'vue'

	import {
		onShow,
		onLoad
	} from "@dcloudio/uni-app"

	const user = ref({})
	const open_id = uni.getStorageSync("open_id")

	onShow(() => {
		// user.value = uni.getStorageSync("user")
		// console.log(user.value);
		if (open_id) {
			getUserInfo()
		} else {
			uni.setStorageSync("user", " ")
		}
	})

	const getUserInfo = () => {
		uni.request({
			url: "http://150.158.39.251:8080/GetUserInfo?open_id=" + open_id,
			method: "GET",
			success: (res) => {
				user.value = res.data
				uni.setStorageSync("user", user.value)
			}
		})
	}

	function toLogin() {
		uni.navigateTo({
			url: "../../pages/login/index"
		})
	}

	function allInfo() {
		uni.navigateTo({
			url: './components/changInfo'
		})
	}

	function myBook() {
		if (open_id) {
			uni.navigateTo({
				url: "./components/bookshelf"
			})
		} else {
			uni.showToast({
				icon: "error",
				title: "请先登录"
			})
		}
	}

	function myPublish() {
		if (open_id) {
			uni.navigateTo({
				url: "./components/publish"
			})
		} else {
			uni.showToast({
				icon: "error",
				title: "请先登录"
			})
		}
	}
</script>

<template>
	<view class="my">
		<view class="info" v-if="open_id">
			<image :src="user.image"></image>
			<!-- <image src="../../static/images/touxiang.jpg"></image>
			 -->
			<view class="base">
				<text class="name">{{user.Name}}</text>
				<text class="uid">{{open_id}}</text>
			</view>
			<view class="all">
				<view @click="allInfo">
					<image class="icon" src="../../static/assets/icon/youjiantou.png" mode=""></image>
				</view>
			</view>
		</view>
		<view v-else class="info">
			<image src="../../static/images/touxiang.jpg"></image>
			<view class="base">
				<text class="name">--</text>
				<text class="uid">--</text>
			</view>
			<view class="login" @click="toLogin">登录</view>
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

			.uid {
				padding: 20rpx;
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

		.login {
			width: 100rpx;
			height: 80rpx;
			color: white;
			text-align: center;
			line-height: 80rpx;
			background-color: #61ba8b;
			border-radius: 15rpx;
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