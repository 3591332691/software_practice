<script setup>
	import {
		ref
	} from 'vue';
	import {
		onLoad
	} from "@dcloudio/uni-app"

	const book_id = ref(0)
	const chapterName = ref("")
	const content = ref("")
	const str = ref("100rpx;")

	onLoad((options) => {
		// console.log(options);
		book_id.value = options.book_id
	})

	const heightStyle = () => {
		if (content.value.length > 100) str.value = '70vh;'
		else str.value = "100rpx;"
	}

	const onSubmit = () => {
		if (chapterName.value.trim() === '') {
			uni.showToast({
				icon: "error",
				title: "请填写章节标题"
			})
			return
		}
		if (content.value.trim() === '') {
			uni.showToast({
				icon: "error",
				title: "请输入内容"
			})
			return
		}
		uni.request({
			url: "http://150.158.39.251:8080/AddNewChapter",
			method: "POST",
			data: {
				book_id: parseInt(book_id.value),
				title: chapterName.value,
				textContent: content.value
			},
			success: (res) => {
				// console.log(res.data);
				uni.showToast({
					icon: 'success',
					title: '上传成功'
				})
				setTimeout(() => {
					uni.redirectTo({
						url: "../../pages/book-detail/index?bid=" + book_id.value +
							'&&from=publish'
					})
				}, 500)
			}
		})
	}
</script>

<template>
	<view class="viewport">
		<view class="form">
			<view class="item">
				<text class="label">标题</text>
				<input class="input" type="text" placeholder="请填写标题" v-model="chapterName" />
			</view>

			<view class="item">
				<text class="label">内容</text>
				<textarea :style="'height:' + str" @input="heightStyle" maxlength="10000"
					v-model="content" class="area"></textarea>
			</view>

			<button @click="onSubmit" class="button">上传</button>
		</view>
	</view>
</template>

<script>
</script>

<style lang="scss">
	Page {
		height: 100%;
	}

	.form {
		margin: 20rpx 20rpx 0;

		.item {
			display: flex;
			padding: 30rpx 0;
			font-size: 28rpx;
			border-bottom: 1rpx solid #ddd;

			&:last-child {
				border: none;
			}

			.label {
				width: 100rpx;
				color: #333;
			}

			.account {
				color: #808080;
			}

			.input {
				flex: 1;
			}

			.area {
				// overflow: auto;
				border: 1rpx solid #ddd;
				line-height: 50rpx;
			}

		}

		.button {
			margin-top: 30rpx;
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