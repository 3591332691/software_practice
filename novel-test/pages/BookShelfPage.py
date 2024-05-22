from base.BasePage import BasePage
from base import Route


class BookShelfPage(BasePage):
    
    locators = {
        "BASE_ELEMENT": "view",
        "BOOK_LIST": "page > view > book-list",
    }

    def check_bookShelfPage_base_element(self):
        """
        校验页面的基本元素
        """
        # 校验页面是否包含view元素
        self.mini.assertTrue(self.mini.page.element_is_exists(BookShelfPage.locators['BASE_ELEMENT']))
        # 校验页面是否包含book-list组件
        self.mini.assertTrue(self.mini.page.element_is_exists(BookShelfPage.locators['BOOK_LIST']))

    def check_bookShelfPage_path(self):
        """
        校验页面路径
        """
        self.mini.assertEqual(self.current_path(), Route.BookShelfPage_route)
        