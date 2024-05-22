```shell
// 开始执行测试
minitest -s suite.json -c config.json -g 

// 查看测试结果
python -m http.server 12345 -d outputs 
http://localhost:12345
```
