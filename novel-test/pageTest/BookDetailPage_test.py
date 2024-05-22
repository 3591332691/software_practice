# coding=utf-8
import os

from base import Route 
from base.BaseCase import BaseCase
from pages.BookDetailPage import BookDetailPage

"""
小程序book详情页测试
"""


class BookDetailPageTest(BaseCase):
    def __init__(self, methodName='runTest'):
        super(BookDetailPageTest, self).__init__(methodName)
        self.bookDetailPage = BookDetailPage(self)
      
    def test_bookDetail_01_path(self):
        """
        case1:测试book详情页的跳转路径是否正确，跳转路径要使用绝对路径
        """
        self.app.navigate_to(Route.BookDetailPage_route)
        self.bookDetailPage.check_bookDetailPage_path()

    def test_bookDetail_02_base_element(self):
        """
        case2:页面的基本元素是否存在
        """
        self.app.navigate_to(Route.BookDetailPage_route)
        self.bookDetailPage.check_bookDetailPage_base_element()