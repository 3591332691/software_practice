<script setup>
	import {
		ref
	} from 'vue';

	const user = uni.getStorageSync("user")
	const open_id = uni.getStorageSync("open_id")
	const newUser = ref({
		open_id: open_id,
		Name: user.Name,
		// image: "../../../static/images/touxiang.jpg",
		image: user.image,
		news: null
	})
	// console.log(newUser.value);

	const getAvater = () => {
		uni.chooseMedia({
			count: 1,
			mediaType: ['image'],
			success: (res) => {
				const {
					tempFilePath
				} = res.tempFiles[0]
				console.log(tempFilePath);
				newUser.value.image = tempFilePath
			}
		})
	}

	const loginOut = () => {
		uni.removeStorageSync("open_id")
		uni.showToast({
			icon: "success",
			title: "退出成功"
		})
		setTimeout(() => {
			uni.reLaunch({
				url: "/pages/user/index"
			})
		}, 500)
	}

	const onChange = () => {
		uni.request({
			url: "http://150.158.39.251:8080/UpdateUser",
			method: "POST",
			dataType: "JSON",
			data: {
				open_id: open_id,
				name: newUser.value.Name,
				image: newUser.value.image,
				news: null
			},
			success: (res) => {
				// console.log(res);
				if (res.data === "Update user successfully") {
					uni.setStorageSync("user", newUser.value)
					uni.showToast({
						icon: "success",
						title: "修改成功"
					})
					setTimeout(() => {
						uni.navigateBack({
							delta: 1
						})
					}, 500)
				} else {
					uni.showToast({
						icon: "error",
						title: "修改失败"
					})
				}
			}
		})
	}
</script>

<template>
	<view class="viewport">
		<view class="form">
			<view class="avatar">
				<image :src="newUser.image" mode=""></image>
				<text @tap="getAvater">点击修改头像</text>
			</view>
			<view class="item">
				<text class="label">账号</text>
				<text class="account">{{open_id}}</text>
			</view>
			<view class="item">
				<text class="label">昵称</text>
				<input class="input" type="text" placeholder="请填写昵称" v-model="newUser.Name" />
			</view>
			<!-- <view class="item">
				<text class="label">性别</text>
				<radio-group>
					<label class="radio">
						<radio value="男" color="#61ba8b" />
						男
					</label>
					<label class="radio">
						<radio value="女" color="#61ba8b" />
						女
					</label>
				</radio-group>
			</view> -->
			<!-- <view class="item">
				<text class="label">生日</text>
				<input class="input" type="text" placeholder="请填写生日" value="01-01" />
			</view> -->
			<view class="btn">
				<button @click="loginOut" class="button">退出登录</button>
				<button @click="onChange" class="button">修 改</button>
			</view>
		</view>
	</view>
</template>

<style lang="scss">
	.form {
		margin: 20rpx 20rpx 0;

		.item,
		{
		display: flex;
		line-height: 100rpx;
		font-size: 28rpx;
		border-bottom: 1rpx solid #ddd;



		&:last-child {
			border: none;
		}

		.label {
			width: 180rpx;
			color: #333;
		}

		.account {
			color: #808080;
		}

		.input {
			flex: 1;
			height: 100rpx;
		}

		.radio {
			margin-right: 20rpx;
		}

	}

	.avatar {
		display: flex;
		flex-direction: column;
		align-items: center;
		margin: 40rpx;

		image {
			margin: 40rpx;
			width: 150rpx;
			height: 150rpx;
			border-radius: 50%;
		}
	}

	.btn {
		display: flex;
	}

	.button {
		flex: 1;
		margin: 30rpx 20rpx;
		height: 80rpx;
		text-align: center;
		line-height: 80rpx;
		color: #fff;
		border-radius: 80rpx;
		font-size: 30rpx;
		background-color: #61ba8b;
	}
	}
</style>