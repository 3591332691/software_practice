from base.BasePage import BasePage
from base import Route


class IndexPage(BasePage):
    """小程序首页公共方法"""

    locators = {
        "BASE_ELEMENT": "view",
        "TOP_BAR": "navbar",
        "SWIPER": "swiper",
        "BOOK_LIST": "book-list",
    }

    # 首页点击第一本小说
    first_book = "view > view:nth-child(1) > view.itemright > view.itemtitle > text"

    def check_indexPage_path(self):
        """
        校验页面路径
        """
        self.mini.assertEqual(self.current_path(), Route.IndexPage_route)
    
    def check_indexPage_base_element(self):
        """
        校验页面的基本元素
        """
        # 校验页面是否包含view元素
        self.mini.assertTrue(self.mini.page.element_is_exists(IndexPage.locators['BASE_ELEMENT']))
        # 校验页面是否包含顶部状态栏
        self.mini.assertTrue(self.mini.page.element_is_exists(IndexPage.locators['TOP_BAR']))
        # 校验页面是否包含轮播图
        self.mini.assertTrue(self.mini.page.element_is_exists(IndexPage.locators['SWIPER']))
        # 校验页面是否包含书架列表
        self.mini.assertTrue(self.mini.page.element_is_exists(IndexPage.locators['BOOK_LIST']))

    def get_first_book(self):
        """
        点击book-list中第一部小说
        """
        element = self.mini.page.get_element(str(self.first_book))
        # element = self.mini.page.get_element("text", inner_text = "宇宙职业选手")
        self.mini.assertIsNotNone(element)
        element.click()