<script setup>
	function changInfo() {
		uni.navigateTo({
			url:'./changInfo'
		})
	}
</script>

<template>
	<view class="viewport">
		<view class="avatar">
			<image class="image" src="../../../static/upload/1.jpg" />
			<text class="text">点击修改头像</text>
		</view>
		<view class="content">
			<view class="item">
				<text class="label">账号</text>
				<text class="text">1234</text>
			</view>
			<view class="item">
				<text class="label">昵称</text>
				<text class="text">123456</text>
			</view>
			<view class="item">
				<text class="label">性别</text>
				<text class="text">女</text>
			</view>
			<view class="item">
				<text class="label">生日</text>
				<view class="text">01</view>
			</view>
		</view>
		<button @click="changInfo" class="button">修 改</button>
	</view>
</template>

<style lang="scss">
	.viewport {
		display: flex;
		flex-direction: column;
		height: 100%;
		background-size: auto 420rpx;
	}

	.avatar {
		padding: 30rpx 0;
		width: 100%;
		text-align: center;
		background-color: #f4f4f4;

		.image {
			width: 160rpx;
			height: 160rpx;
			border-radius: 50%;
			background-color: #eee;
		}

		.text {
			display: block;
			padding-top: 20rpx;
			line-height: 1;
			font-size: 26rpx;
			color: #808080;
		}
	}

	.content {
		padding: 0 20rpx;

		.item {
			display: flex;
			padding: 30rpx 20rpx;
			font-size: 28rpx;
			border-bottom: 1rpx solid #ddd;

			.label {
				width: 180rpx;
				color: #333;
			}

			.text {
				color: #666;
			}
		}
	}
	
	.button {
		margin: 30rpx 20rpx;
		height: 80rpx;
		color: #fff;
		font-size: 30rpx;
		line-height: 80rpx;
		border-radius: 80rpx;
		background-color: #61ba8b;
	}
</style>