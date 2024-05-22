# coding=utf-8
import os

from base import Route 
from base.BaseCase import BaseCase
from pages.UserPage import UserPage

"""
“我的”页面测试
"""

class UserPageTest(BaseCase):
    def __init__(self, methodName='runTest'):
        super(UserPageTest, self).__init__(methodName)
        self.userPage = UserPage(self)

    def test_user_01_path(self):
        """
        case1:测试“我的”页面的跳转路径是否正确，跳转路径要使用绝对路径
        """
        self.app.switch_tab(Route.UserPage_route)
        self.userPage.check_userPage_path()

    def test_user_02_base_element(self):
        """
        case2:页面的基本元素是否存在
        """
        self.app.switch_tab(Route.UserPage_route)
        self.userPage.check_userPage_base_element()