from base.BasePage import BasePage
from base import Route


class SearchPage(BasePage):

    locators = {
        "BASE_ELEMENT": "view",
        "SEARCH": "page > view > view.search",
        "RECOMMAND": "page > view > view.recommand",
    }

    def check_searchPage_base_element(self):
        """
        校验页面的基本元素
        """
        # 校验页面是否包含view元素
        self.mini.assertTrue(self.mini.page.element_is_exists(SearchPage.locators['BASE_ELEMENT']))
        # 校验页面是否包含搜索框
        self.mini.assertTrue(self.mini.page.element_is_exists(SearchPage.locators['SEARCH']))
        # 校验页面是否包含搜索推荐列表
        self.mini.assertTrue(self.mini.page.element_is_exists(SearchPage.locators['RECOMMAND']))

    def check_searchPage_path(self):
        """
        校验页面路径
        """
        self.mini.assertEqual(self.current_path(), Route.SearchPage_route)