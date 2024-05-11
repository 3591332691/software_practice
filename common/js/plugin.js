export function getUserInfo(openId) {
  const url = `http://localhost:8080/GetUserInfo?open_id=${openId}`;

  return fetch(url)
    .then(response => {
      if (!response.ok) {
        throw new Error('Network response was not ok');
      }
      return response.json(); // 如果后端返回 JSON 数据
    })
    .then(data => {
      return data; // 返回获取到的用户信息
    })
    .catch(error => {
      console.error('There was a problem with your fetch operation:', error);
    });
}

