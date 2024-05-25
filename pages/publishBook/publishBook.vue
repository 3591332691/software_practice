<script setup>
	import {
		ref
	} from 'vue';
	import {
		onLoad
	} from "@dcloudio/uni-app"

	const user = uni.getStorageSync("user")

	const open_id = uni.getStorageSync("open_id")
	const book = ref({
		book_id: 0,
		bookName: null,
		brief_introduction: null,
		tag: null,
		author_id: null,
		image: null
	})

	const selectedTags = ref([]) // 存储选中的标签值  
	let tags = [{
			name: '历史',
			value: '1'
		},
		{
			name: '现代',
			value: '2'
		},
		{
			name: '未来',
			value: '3'
		}
	]

	function isSelected(tagValue) {
		return selectedTags.value.includes(tagValue);
	}
	
	function toggleSelection(tagValue) {
		const index = selectedTags.value.indexOf(tagValue);
		if (index !== -1) {
			selectedTags.value.splice(index, 1);
		} else {
			selectedTags.value.push(tagValue);
		}
	}

	onLoad((options) => {
		book.value.book_id = options.book_id
		book.value.bookName = options.book_name
		book.value.author = options.author_name
		book.value.brief_introduction = options.brief_introduction !== "undefined" ? options.brief_introduction :
			null
		book.value.image = options.cover !== "undefined" ? options.cover : null
	})

	const getCover = () => {
		uni.chooseMedia({
			count: 1,
			mediaType: ['image'],
			success: (res) => {
				const {
					tempFilePath
				} = res.tempFiles[0]
				console.log(tempFilePath);
				book.value.image = tempFilePath
			}
		})
	}
	const url = ref({})
	let type = 0
	const onSubmit = async () => {
		book.value.tag = selectedTags.value.join('&')
		if (book.value.book_id) {
			url.value = "http://150.158.39.251:8080/UpdateBook"
			type = 0
		} else {
			url.value = "http://150.158.39.251:8080/createBook"
			type = 1
		}
		const res = await uni.request({
			url: url.value,
			method: "POST",
			dataType: "JSON",
			data: {
				"book_id": parseInt(book.value.book_id),
				"book_name": book.value.bookName,
				"brief_introduction": book.value.brief_introduction,
				"tag": book.value.tag,
				"author_id": open_id,
				"image": book.value.image
			},
			success: (res) => {
				// console.log(book.value.image);
				if (type === 0) {
					if (res.statusCode === 200) {
						uni.showToast({
							icon: "success",
							title: "更新成功"
						})
						setTimeout(() => {
							uni.redirectTo({
								url: "../../pages/book-detail/index?bid=" + book.value
									.book_id +
									'&&from=publish'
							})
						}, 500)
					} else {
						uni.showToast({
							icon: "error",
							title: "更新失败"
						})
					}
				} else {
					if (res.statusCode === 200) {
						uni.showToast({
							icon: "success",
							title: "创建成功"
						})
						setTimeout(() => {
							uni.navigateBack({
								delta: 1
							})
						}, 500)
					} else {
						uni.showToast({
							icon: "error",
							title: "创建失败"
						})
					}
				}
			}
		})
	}
</script>

<template>
	<view class="viewport">
		<view class="form">
			<view class="item">
				<text class="label">书名</text>
				<input class="input" type="text" placeholder="请填写书名" v-model="book.bookName" />
			</view>

			<view class="item">
				<text class="label">作者</text>
				<text class="account">{{user.Name}}</text>
			</view>

			<view class="item">
				<text class="label">简介</text>
				<textarea v-model="book.brief_introduction" class="area" name="" id="" cols="30" rows="10"></textarea>
			</view>

			<view class="item">
				<text class="label">封面</text>
				<image @tap="getCover" v-if="book.image" :src="book.image" mode=""
					style="max-width: 100px; max-height: 100px;"></image>
				<!-- <input type="file"  accept="image/*"> -->
				<text v-else @tap="getCover">点击选择图片</text>
			</view>

			<view class="item">
				<text class="label">类别</text>
				<view class="account">
					<view v-for="(tag, index) in tags" :key="index" @click="toggleSelection(tag.value)" class="tag"
						:class="{ selected: isSelected(tag.value) }">{{tag.name}}</view>
				</view>
				<!-- 待写选择框 -->
			</view>
			<button @click="onSubmit" class="button">上传</button>
		</view>
	</view>
</template>

<script>
</script>

<style lang="scss">
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
				display: flex;
				color: #808080;
			}

			.input {
				flex: 1;
			}

			.area {
				flex: 1;
				border: 1rpx solid #ddd;
				line-height: 50rpx;
			}

			.cover {
				display: none;
			}

			.tag {
				margin-right: 20rpx;
				padding: 5rpx 8rpx;
				height: 30rpx;
				line-height: 30rpx;
				font-size: 24rpx;
				font-weight: 500;
				border: 1rpx solid #ddd;
				border-radius: 30rpx;
			}

			.selected {
				color: white;
				background-color: #61ba8b;
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