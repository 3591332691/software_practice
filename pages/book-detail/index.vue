<template>
  <!-- 顶部区域 -->
  <!-- <view class="header">{{book.title}}免费在线阅读</view> -->

  <!-- 小说区域 -->
  <view class="books">
    <view class="book_info">
      <view class="cover">
        <image :src="book.img" style="height: 300rpx;width: 240rpx;" :alt="book.title"></image>
      </view>
      <view class="book_box">
        <view class="name">{{book.title}}</view>
        <view class="dd_box">
          <span class="dd">作者：{{book.author}}</span>
          <span class="dd">分类：{{book.tag}}</span>
          <span class="dd">状态：{{book.state}}</span>
          <span class="dd">章节数：{{book.content.length}}</span>
          <span class="dd">更新：{{book.update}}</span>
        </view>
      </view>
    </view>
	
	<view class="read_link">
	      <view class="button">
	        <button >加入书架</button>
	        <button style="margin-left: 2%;">开始阅读</button>
	      </view>
	    </view>

    <view class="book_about">
      <view class="dt">内容简介</view>
      <view class="book_about_dd" style="white-space:pre-wrap">{{book.profile}}</view>
    </view>

    <view class="book_last">
      <view class="dt">目录</view>
      <view v-for="(item,index) in book.content" :key="index">
        <view class="book_last_dd">{{item.title}}</view>
      </view>
    </view>
  </view>

</template>

<script setup>
  import {
    ref,
    defineProps,
    toRaw
  } from 'vue'

  import {
    onLoad
  } from '@dcloudio/uni-app'

  const books = ref([])
  const book = ref({})
  const book_index = ref(0)

  onLoad((options) => {
    getMockBookDetail(options)
  })

  const getMockBookDetail = (options) => {
    const app = getApp()
    books.value = app.globalData.mockData.books
    const booksData = toRaw(books.value)

    if (options.bid > 0) {
      for (let i = 0; i < booksData.length; i++) {
        if (booksData[i].id == options.bid) {
          book_index.value = booksData[i].id
          book.value = booksData[i]
          break;
        }
      }
    }

    console.log(book.value)
  }
</script>

<style>
  /* .header {
    height: 90rpx;
    line-height: 90rpx;
    text-align: center;
    font-size: 32rpx;
    color: #fff;
    text-shadow: 2rpx 2rpx #2680aa;
    background-size: 272rpx 90rpx;
    background-color: #68aac2;
    border-top: 1px #8ec5d9 solid;
    position: relative;
    overflow: hidden;
  } */

  .dt {
    /* background: #61ba8b; */
    line-height: 80rpx;
    /* color: #888; */
    padding-left: 20rpx;
    border-bottom: 2rpx solid #EEE;
  }

  .book_box {
    padding-left: 270rpx;
    height: 300rpx;
  }

  .book_info {
    display: block;
    position: relative;
    overflow: hidden;
	border: 1rpx solid #efefef;
	border-radius: 8rpx;
	margin: 15rpx;
	box-shadow: 1rpx 1rpx 15rpx #ddd;
  }

  .book_info .cover {
    position: absolute;
    left: 0;
    top: 0;
  }

  .book_box dl dd span {
    display: block;
    width: 50%;
    float: left;
    white-space: nowrap;
    text-overflow: ellipsis;
    overflow: hidden;
  }

  .dd {
    display: block;
    unicode-bidi: isolate;
  }

  .book_about_dd {
    padding: 10px;
    line-height: 150%;
    color: #888;
  }

  .book_last_dd {
    line-height: 84rpx;
    margin: 0 20rpx;
    xpadding: 0 20rpx;
    border-bottom: 2rpx solid #EEE;
	color: #888;
    font-size: 28rpx;
    white-space: nowrap;
    text-overflow: ellipsis;
    overflow: hidden;
  }

  .readlink {
    margin: 20rpx;
    line-height: 80rpx;
    height: 80rpx;
    overflow: hidden;
  }

  .button {
        display: flex;
        text-align: center;
        font-size: 16px;
        border-radius: 3px;
  }

  button {
    margin-top: 10rpx;
    margin-bottom: 10rpx;
    width: 47%;
	color: #fff;
	background-color: #61ba8b;
    text-align: center;
  }
</style>