<template>
  <view >
    <!-- 小说列表 -->
    <view class="item" v-for="(item,index) in books" :key="item.id" :data-bid="item.id" @click="goDetail">
      <view class="itemleft">
        <image class="img" :src="item.img"></image>
      </view>
      <view class="itemright">
        <view class="itemtitle"><text>{{item.title}}</text></view>
        <view class="itemprofile"><text>小说简介：{{item.profile}}</text></view>
        <view class="itemtauthor"><text>作者：{{item.author}}</text></view>
        <view class="itemupdate"><text>更新日期：{{item.update}}</text></view>
      </view>
    </view>
  </view>
</template>

<script setup>
  import {
    ref,
    onBeforeMount,
    defineProps
  } from 'vue'
  // import http from "../../common/js/getDemoData.js"

  const books = ref([])

  onBeforeMount(() => {
    getBooks()
  })

  const getBooks = () => {
    // uni.showLoading({
    //   title: '加载中'
    // });
    
    // http.gethot(res => {

    //   uni.hideLoading();
    //   books.value = res;
    //   console.log(hot);
    // });
    
    const app = getApp()
    books.value = app.globalData.mockData.books
  }
  
  // 跳转到小说详情页
  const goDetail = (e) => {
    uni.navigateTo({
      url: '../../pages/book-detail/index?bid=' + e.currentTarget.dataset.bid
    })
  }
</script>

<style>
  .item {
    display: flex;
    padding: 15rpx;
    border: 1rpx solid #efefef;
    border-radius: 8rpx;
    margin: 15rpx;
    box-shadow: 1rpx 1rpx 15rpx #ddd;
  }

  .itemleft image {
    height: 400rpx;
    width: 300rpx;
    display: block;
    margin-right: 15rpx;
  }

  .itemright {
    display: flex;
    flex-direction: column;
    justify-content: space-around;
    font-size: 30rpx;
  }

  .itemtitle {
    padding-top: 5px;
    padding-left: 0px;
    font-family: Arial, Helvetica, sans-serif;
    overflow: hidden;
    text-overflow: ellipsis;
    white-space: nowrap;
    font-weight: bold;
    font-size: 16px;
  }

  .itemprofile {
    margin-top: 10px;
    font-size: 12px;
    overflow: hidden;
    text-overflow: ellipsis;
    display: -webkit-box;
    -webkit-line-clamp: 4;
    -webkit-box-orient: vertical;
  }

  .img {
    border-radius: 10px;
    display: flex;
    height: 100%;
    width: auto;
  }

  .itemauthor {
    margin-top: 10px;
    font-size: 14px;
    overflow: hidden;
    text-overflow: ellipsis;
    white-space: nowrap;
  }
</style>