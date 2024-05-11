<template>
  <view class="content">
    <!-- 顶端状态栏 -->
    <navbar :isHome="true" />
	
    <!-- 公告栏 -->
    <view :style="'margin-top:' + navbar_height + 'rpx;'">
      <view class="weui-cell" style="background:#fff9eb;">
        <view class="weui-cell__hd">
          <image src="/static/images/ic_myapp.png"
            style="display: block; width: 40rpx; height: 40rpx; margin-right: 14rpx;"></image>
        </view>
        <view class="weui-cell__bd">
          <text style="color:#be9719; font-size: 26rpx;">点击右上角“添加到我的小程序”，方便下次找到！</text>
        </view>
        <view class="weui-cell__ft">
          <image src="/static/images/modal_closer.png" style="display: block;width: 15px; height: 15px;"></image>
        </view>
      </view>
    </view>

    <!-- 轮播图 -->
    <view v-if="slides && slides.length > 0" class="index-swiper">
      <swiper autoplay circular :interval="4000" :duration="500" indicator-dots style="height: 400rpx;">
        <block v-for="(item, index) in slides" :key="index">
          <swiper-item>
            <image :src="item.pic_image_url" mode="widthFix" show-menu-by-longpress :data-index="index"></image>
          </swiper-item>
        </block>
      </swiper>
    </view>

    <!-- 推荐小说列表 -->
    <book-list />
  </view>
</template>

<script setup>
  import {
    ref,
    reactive,
    computed,
    onBeforeMount,
  } from 'vue'
  import {
    onLoad
  } from '@dcloudio/uni-app'

  const slides = ref([])
  const navbar_height = ref(130)

  const app = getApp()

  onBeforeMount(() => {
    uni.$once('updateNavbarHeight', function(data) {
      navbar_height.value = data.height
    })
  })
  onLoad(() => {
    app.globalData.utils.getUserInfo()
    slides.value = app.globalData.mockData.slides
  })
</script>

<style>
  .page {
    background: #fff;
  }

  .index-swiper {
    padding: 20rpx 20rpx 0 20rpx;
    overflow: hidden;
  }

  .index-swiper swiper {
    height: 320rpx;
    overflow: hidden;
    border-radius: 20rpx;
  }

  .index-swiper swiper-item image {
    width: 100%;
    height: 100%;
  }
</style>