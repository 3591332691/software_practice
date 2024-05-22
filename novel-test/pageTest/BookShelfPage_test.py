# coding=utf-8
import os

from base import Route 
from base.BaseCase import BaseCase
from pages.BookShelfPage import BookShelfPage

"""
书架页面测试
"""

class BookShelfPageTest(BaseCase):
    def __init__(self, methodName='runTest'):
        super(BookShelfPageTest, self).__init__(methodName)
        self.bookShelfPage = BookShelfPage(self)

    def test_bookShelf_01_path(self):
        """
        case1:测试书架的跳转路径是否正确，跳转路径要使用绝对路径
        """
        self.app.switch_tab(Route.BookShelfPage_route)
        self.bookShelfPage.check_bookShelfPage_path()

    def test_bookShelf_02_base_element(self):
        """
        case2:页面的基本元素是否存在
        """
        self.app.switch_tab(Route.BookShelfPage_route)
        self.bookShelfPage.check_bookShelfPage_base_element()