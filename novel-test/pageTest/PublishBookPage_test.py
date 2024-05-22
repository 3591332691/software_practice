# coding=utf-8
import os

from base import Route 
from base.BaseCase import BaseCase
from pages.PublishBookPage import PublishBookPage

"""
搜索页面测试
"""

class PublishBookPageTest(BaseCase):
    def __init__(self, methodName='runTest'):
        super(PublishBookPageTest, self).__init__(methodName)
        self.publishBookPage = PublishBookPage(self)

    def test_publishBook_01_path(self):
        """
        case1:发布页面跳转路径是否正确，跳转路径要使用绝对路径
        """
        self.app.navigate_to(Route.PublishBookPage_route)
        self.publishBookPage.check_publishBookPage_path()

    def test_publishBook_02_base_element(self):
        """
        case2:页面的基本元素是否存在
        """
        self.app.navigate_to(Route.PublishBookPage_route)
        self.publishBookPage.check_publishBookPage_path()