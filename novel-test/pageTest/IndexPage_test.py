# coding=utf-8
import os

from base import Loader
from base.BaseCase import BaseCase
from pages.IndexPage import IndexPage

"""
主界面测试
"""


class IndexPageTest(BaseCase):
    def __init__(self, methodName='runTest'):
        super(IndexPageTest, self).__init__(methodName)
        self.indexPage = IndexPage(self)

    def test_index_01_index_path(self):
        """
        case1:测试主界面的跳转路径是否正确，跳转路径要使用绝对路径,小程序默认进入就是首页，所以不用再切换进入的路径
        """
        self.indexPage.check_indexPage_path()

    def test_index_02_base_element(self):
        """
        case2:页面的基本元素是否存在
        """
        self.indexPage.check_indexPage_base_element()

    def test_index_03_top_bar(self):
        """
        case3:检查首页顶部状态栏的文字是否正确显示
        """
        self.assertTexts(["阅读平台", "找小说"], "text")

    def test_index_04_click_first_book(self):
        """
        case4:从首页点击第一本小说跳转到小说页面
        """
        # 点击首页的第一本小说
        self.indexPage.get_first_book()
        result = self.page.wait_for("page")  # 等待页面渲染完成

if __name__ == "__main__":
    Loader.run(module="case.indexpage_test", config="../config.json", generate_report=True)