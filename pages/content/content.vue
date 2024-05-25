<script setup>
	import {
		onLoad
	} from "@dcloudio/uni-app"
	import {
		ref
	} from "vue";

	const book_id = ref(0)
	const chapter_id = ref(0)
	const content = ref({})
	const cNum = ref(0)
	onLoad((options) => {
		// console.log(options);
		book_id.value = options.book_id
		chapter_id.value = options.chapter_id
		cNum.value = options.chapter_num
		getContent(book_id.value, chapter_id.value)
	})

	const getContent = (bid, cid) => {
		uni.request({
			url: "http://150.158.39.251:8080/GetChapterContent",
			method: "GET",
			data: {
				book_id: bid,
				chapter_id: cid
			},
			success: (res) => {
				// console.log(res);
				content.value = res.data
				// console.log(content.value);
			}
		})
	}

	const prevChapter = () => {
		if (chapter_id.value - 1 > 0) {
			getContent(book_id.value, --chapter_id.value)
		} else {
			uni.showToast({
				icon: "error",
				title: "没有上一章了"
			})
		}
	}

	const nextChapter = () => {
		if (chapter_id.value + 1 <= cNum.value) {
			getContent(book_id.value, ++chapter_id.value)
		} else {
			uni.showToast({
				icon: "error",
				title: "没有上一章了"
			})
		}
	}
	
	const catalog = () => {
		uni.navigateBack({
			delta: 1
		})
	}
</script>

<template>
	<view class="viewport">
		<view class="title">{{ "第 " + chapter_id  + ' 章'}}</view>
		<view class="content">{{content}}</view>
		<view class="choose">
			<button class="button" @click="prevChapter">上一章</button>
			<button class="button" @click="catalog">目 录</button>
			<button class="button" @click="nextChapter">下一章</button>
		</view>
	</view>
</template>

<style lang="scss">
	page {
		height: 100%;
	}

	.viewport {
		display: flex;
		flex-direction: column;
		height: 95%;
		padding: 30rpx;
		background-color: #fff;

		.title {
			margin-bottom: 30rpx;
			font-size: 36rpx;
			font-weight: 500;
		}

		.content {
			flex: 1;
		}

		.choose {
			display: flex;
			justify-content: space-between;
		}

		.button {
			height: 50rpx;
			font-size: 26rpx;
			color: #888;
			line-height: 50rpx;
		}
	}
</style>