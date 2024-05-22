# coding=utf-8
import os

from base import Route 
from base.BaseCase import BaseCase
from pages.SearchPage import SearchPage

"""
搜索页面测试
"""

class SearchPageTest(BaseCase):
    def __init__(self, methodName='runTest'):
        super(SearchPageTest, self).__init__(methodName)
        self.searchPage = SearchPage(self)

    def test_search_01_path(self):
        """
        case1:搜索页面跳转路径是否正确，跳转路径要使用绝对路径
        """
        self.app.navigate_to(Route.SearchPage_route)
        self.searchPage.check_searchPage_path()

    def test_search_02_base_element(self):
        """
        case2:页面的基本元素是否存在
        """
        self.app.navigate_to(Route.SearchPage_route)
        self.searchPage.check_searchPage_path()