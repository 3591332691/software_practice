from base.BasePage import BasePage
from base import Route


class BookDetailPage(BasePage):

    locators = {
        "BASE_ELEMENT": "view",
        "BOOK_INFO": "page > view.books",
        "READ_LINK": "page > view > view.read_link",
    }

    def check_bookDetailPage_base_element(self):
        """
        校验页面的基本元素
        """
        # 校验页面是否包含view元素
        self.mini.assertTrue(self.mini.page.element_is_exists(BookDetailPage.locators['BASE_ELEMENT']))
        # 校验页面是否包含书的基本信息
        self.mini.assertTrue(self.mini.page.element_is_exists(BookDetailPage.locators['BOOK_INFO']))
        # 校验页面是否包含相关按钮和链接
        self.mini.assertTrue(self.mini.page.element_is_exists(BookDetailPage.locators['READ_LINK']))

    def check_bookDetailPage_path(self):
        """
        校验页面路径
        """
        self.mini.assertEqual(self.current_path(), Route.BookDetailPage_route)