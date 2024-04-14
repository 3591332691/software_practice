const Baseurl = "https://bxwx7.com/"

function gethot(callback) {
  uni.request({
    url : Baseurl,
    method: "GET",
    header: {
      'Accept': 'application/json',
      'Content-Type': 'application/json',
      'Access-Control-Allow-Origin': '*'
    },
    success: (res) => {
      const str = res.data;
      const zz = /<div class="shu_box1"><p class="p_img"><a href="([\s\S]*?)" title="([\s\S]*?)"><img src="([\s\S]*?)" alt="[\s\S]*?"><\/a><\/p><p class="line20"><a href="[\s\S]*?" title="[\s\S]*?">[\s\S]*?<\/a><\/p><p class="line20">作者：([\s\S]*?)<\/p><p class="line20">更新：([\s\S]*?)<\/p><\/div>/g;
      const hot = str.match(zz);
      var hotarr = [];
      hot.forEach((item, index, arr) => {
        const zz = /<div class="shu_box1"><p class="p_img"><a href="([\s\S]*?)" title="([\s\S]*?)"><img src="([\s\S]*?)" alt="[\s\S]*?"><\/a><\/p><p class="line20"><a href="[\s\S]*?" title="[\s\S]*?">[\s\S]*?<\/a><\/p><p class="line20">作者：([\s\S]*?)<\/p><p class="line20">更新：([\s\S]*?)<\/p><\/div>/;
        const book = item.match(zz);
        var obj = {
          'img': book[3],
          'title': book[2],
          'url': book[1],
          'author': book[4],
          'update': book[5],
          'profile': '好看的小说'
        }
        hotarr.push(obj);
      })
      
      try {
        uni.setStorageSync("hotlist", hotarr);
        // console.log(hotarr)
      } catch (e) {};
      callback(hotarr);
    },
    fail: (res) => {
      callback(200);
    }
  });
}

export default {
  gethot,
}