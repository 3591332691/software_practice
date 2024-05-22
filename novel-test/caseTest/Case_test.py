# coding=utf-8
import os

from base import Route
from base.BaseCase import BaseCase
from pages.IndexPage import IndexPage
from pages.BookDetailPage import BookDetailPage
from pages.BookShelfPage import BookShelfPage
from pages.UserPage import UserPage
from pages.SearchPage import SearchPage
from pages.PublishBookPage import PublishBookPage

"""
测试具体用例
"""


class CaseTest(BaseCase):
    def __init__(self, methodName='runTest'):
        super(CaseTest, self).__init__(methodName)
        self.indexPage = IndexPage(self)
        self.bookDetailPage = BookDetailPage(self)
        self.bookShelfPage = BookShelfPage(self)
        self.userPage = UserPage(self)
        self.searchPage = SearchPage(self)
        self.publishBookPage = PublishBookPage(self)

        self.selectors = {
            'FIRSTBOOK' : "view > view:nth-child(1) > view.itemright > view.itemtitle > text",
            "BACK" : '',
            'SEARCH' : "navbar >>> view.nav > view.headNav > view > navigator",
        }

    def test_case_01(self):
        """
        用例编号：01

        用例描述：
        打开微信小程序，首页可以看到推荐小说列表。点击小说列表中的某一本书籍，可以跳转至
        该小说详情页。点击左上角回退按钮，返回首页。

        主要测试功能点：
        1. 首页推荐小说列表。
        2. 推荐列表->书本详情的跳转。
        3. 书本详情的的显示。
        4. 页面栈大于2时可以点击回退按钮返回上一页。

        预期结果：
        1. 打开小程序，首页正常显示，推荐小说列表中至少有一本小说。
        2. 点击小说列表中的某本书籍后，页面正常跳转至书本详情页。
        3. 书本详情页正常显示，且小说简介、封面、作者等信息被正常显示。
        4. 点击左上角回退按钮，返回首页。
        """
        self.indexPage.check_indexPage_path()
        self.indexPage.check_indexPage_base_element()

        self.indexPage.get_first_book()
        self.page.wait_for("page")
        self.bookDetailPage.check_bookDetailPage_base_element()


        self.app.navigate_back()

        self.indexPage.check_indexPage_path()
        self.indexPage.check_indexPage_base_element()

    def test_case_02(self):
        """
        用例编号：02

        用例描述：
        打开微信小程序，显示主界面。点击底部导航栏“书架”按钮跳转至书架页面，
        点击底部导航栏“我的”按钮跳转至用户信息界面。再点击“首页”按钮跳转回
        主界面。

        主要测试功能点：
        1. 通过底部导航栏在页面间跳转。

        预期结果：
        1. 打开小程序，首页正常显示。
        2. 点击底部导航栏“书架”按钮后，页面正常跳转至书架页面且页面正常显示。
        3. 点击底部导航栏“我的”按钮后，页面正常跳转至用户信息页面且正常显示。
        4. 点击底部导航栏“首页”按钮后，页面正常跳转至主页且正常显示。
        """

        self.indexPage.check_indexPage_path()
        self.indexPage.check_indexPage_base_element()

        self.app.switch_tab(Route.BookShelfPage_route)
        self.bookShelfPage.check_bookShelfPage_path()
        self.bookShelfPage.check_bookShelfPage_base_element()
        
        self.app.switch_tab(Route.UserPage_route)
        self.userPage.check_userPage_path()
        self.userPage.check_userPage_base_element()

        self.app.switch_tab(Route.IndexPage_route)
        self.indexPage.check_indexPage_path()
        self.indexPage.check_indexPage_base_element()


    def test_case_03(self):
        """
        用例编号：03

        用例描述：
        打开微信小程序，显示主界面。点击主界面中的轮播图，跳转至对应的小说界面。

        主要测试功能点：
        1. 点击轮播图跳转至对应小说的详情页

        预期结果：
        1. 打开小程序，首页轮播图正常显示。
        2. 点击轮播图后跳转至对应小说详情页面，且页面正常显示。
        """

        self.assertFalse(1)
        # TODO

    def test_case_04(self):
        """
        用例编号：04

        用例描述：
        首页点击底部导航栏“书架”按钮，跳转至书架页面。点击书架页面中的某本小说，
        跳转至该小说的详情页面。点击左上角回退按钮，返回书架页面。

        主要测试功能点：
        1. 底部导航栏跳转。
        2. 书架->书本详情页跳转。
        3. 页面栈大于2时可以点击回退按钮返回上一页。

        预期结果：
        1. 打开小程序，首页正常显示。
        2. 点击导航栏书架按钮后跳转至暑假页面，且页面正常显示并包含至少一本书籍。
        3. 点击某本小说后跳转至对应书本详情页，且页面正常显示。
        4. 点击左上角回退按钮，返回书架页面。
        """

        self.indexPage.check_indexPage_path()
        self.indexPage.check_indexPage_base_element()

        self.app.switch_tab(Route.BookShelfPage_route)
        self.bookShelfPage.check_bookShelfPage_path()
        self.bookShelfPage.check_bookShelfPage_base_element()

        self.page.get_element(self.selectors['FIRSTBOOK']).click()
        self.page.wait_for("page")
        self.bookDetailPage.check_bookDetailPage_base_element()

        self.app.navigate_back()
        self.bookShelfPage.check_bookShelfPage_path()
        self.bookShelfPage.check_bookShelfPage_base_element()

    def test_case_05(self):
        """
        用例编号：05

        用例描述：
        首页点击顶部搜索框，跳转至搜索界面。在搜索框中输入作者名或书名，
        获取搜索结果小说列表。点击搜索结果中的某本小说，跳转至对应小说
        详情页面。

        主要测试功能点：
        1. 顶部搜索框跳转至搜索页面。
        2. 搜索结果的返回与显示。
        3. 搜索结果->小说详情页跳转。

        预期结果：
        1. 打开小程序，首页正常显示。
        2. 点击顶部搜索框跳转至搜索页面，且页面正常。
        3. 在搜索框中输入作者或书名后正常显示搜索结果。
        4. 点击搜索结果中的某本小说，跳转至小说详情页。
        """

        self.indexPage.check_indexPage_path()
        self.indexPage.check_indexPage_base_element()

        self.page.get_element(self.selectors['SEARCH']).click()

        self.page.wait_for("page")

        self.searchPage.check_searchPage_path()
        self.searchPage.check_searchPage_base_element()

        self.assertFalse(1)

    def test_case_06(self):
        """
        用例编号：06

        用例描述：
        用户在首页点击底部导航栏“我的”按钮，跳转至用户信息界面。
        点击“我的发表”按钮，显示我已经发布的书的列表。点击“去发布”
        按钮，跳转至小说发布页面。

        主要测试功能点：
        1. 底部导航栏“我的”跳转至用户信息页面。
        2. “我的发表”跳转。
        3. “发布页面”跳转。

        预期结果：
        1. 打开小程序，首页正常显示。
        2. 点击底部导航栏“我的”按钮跳转至用户信息页面，且页面正常。
        3. 点击“我的发表”按钮，正常显示已发布的书的页面。
        4. 点击“去发布按钮”，跳转至书本发布页面且页面正常显示。
        """

        self.indexPage.check_indexPage_path()
        self.indexPage.check_indexPage_base_element()

        # TODO