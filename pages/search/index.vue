<script setup>
</script>

<template>
	<view class="viewport">
		<view class="search">
			<image class="img" src="../../static/assets/icon/sousuo.png" mode=""></image>
			<input class="input" type="text" placeholder="请输入书名或作者名" />
			<!-- <view class="cancle">x</view> -->
		</view>
		<view class="recommand">
			<view class="title">猜你想看</view>
			<view class="content">
				<view class="item active">
					<text class="text">1</text>
				</view>
				
				<view class="item">
					<text class="text">1</text>
				</view>
				
				<view class="item">
					<text class="text">1</text>
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
		
		.cancle {
			width: 50rpx;
			height: 50rpx;
			background-color: rgb(185, 185, 185);
			color: #fff;
			text-align: center;
			border-radius: 50%;
		}
	}
	
	.recommand {
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
			
			.active {
				background: linear-gradient(to right, #f4f4f4, #fff);				
				border-radius: 15rpx;
			}
		}
	}

</style>
