<script setup>
	import {
		ref
	} from 'vue';

	const open_id = parseInt(uni.getStorageSync("__DC_STAT_UUID").slice(-6))
	// console.log(open_id);
	const doLogin = async () => {
		uni.request({
			url: "http://150.158.39.251:8080/AddUser",
			method: "POST",
			dataType: "JSON",
			data: {
				open_id: open_id,
				name: "默认用户",
				image: null
			},
			success: (res) => {
				if (res.statusCode === 200) {
					uni.setStorageSync("open_id", open_id)
					uni.showToast({
						icon: 'success',
						title: '登录成功'
					})
					setTimeout(() => {
						// uni.switchTab({
						// 	url: "../../pages/user/index",
						// })
						// uni.navigateBack({
						// 	delta: 1
						// })
						uni.reLaunch({
							url:"/pages/user/index"
						})
					}, 500)
				}
			}
		})
	}
</script>

<template>
	<view class="login">
		<input class="input" type="text" placeholder="请输入用户名/手机号码" />

		<input class="input" type="text" placeholder="请输入验证码" />
		<button class="button">登录</button>

		<view class="extra">
				<view class="caption">
					<text>其他登录方式</text>
				</view>
				
				<view class="options">
					<text @click="doLogin" class="icon icon-phone">快捷登录</text>
				</view>
				
			</view>
	</view>
</template>

<style lang="scss">
	Page {
		display: flex;
		align-items: center;
		height: 100%;
	}


	.login {
		width: 100%;
		height: 60vh;
		padding: 15rpx;

		.input {
			height: 80rpx;
			font-size: 28rpx;
			border-radius: 72rpx;
			border: 1px solid #ddd;
			padding-left: 30rpx;
			margin-bottom: 20rpx;
		}

		.button {
			width: 100%;
			height: 80rpx;
			font-size: 28rpx;
			line-height: 80rpx;
			border-radius: 72rpx;
			color: #fff;
			background-color: #61ba8b;
		}
	}

	.extra {
		display: flex;
		flex-direction: column;
		align-items: center;
		margin-top: 70rpx;


		.caption {
			width: 440rpx;
			line-height: 1;
			border-top: 1rpx solid #ddd;
			font-size: 26rpx;
			color: #999;
			position: relative;

			text {
				transform: translate(-40%);
				background-color: #fff;
				position: absolute;
				top: -12rpx;
				left: 50%;
			}
		}

		.options {
			display: flex;
			justify-content: center;
			align-items: center;
			margin-top: 70rpx;
			font-size: 26rpx;
			color: #999;
		}
	}
</style>