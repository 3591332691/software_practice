package com.example.backend;

import com.example.backend.Entity.Book;
import com.example.backend.controller.BookController;
import com.example.backend.service.BookService;
import com.google.gson.Gson;
import io.micrometer.observation.transport.RequestReplyReceiverContext;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.mock.http.server.reactive.MockServerHttpRequest.post;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class BookControllerTests {

	@Autowired
	private MockMvc mockMvc;
	@InjectMocks
	private BookController bookController;
	@Mock
	private BookService bookService;
	@Mock
	private Book book;

	@BeforeEach
	public void setup() {
		MockitoAnnotations.initMocks(this);
		// 初始化MockMvc
		mockMvc = MockMvcBuilders.standaloneSetup(bookController)
				.build();
	}

	@Test
	public void testCreateBook() throws Exception {
		// 模拟一个成功创建书籍的场景
		Map<String, Object> createBookData = new HashMap<>();
		createBookData.put("book_name", "测试书籍");
		createBookData.put("brief_introduction", "这是一本测试书籍");
		createBookData.put("tag", "测试");
		createBookData.put("image", "null");
		createBookData.put("author_id", 1);
		createBookData.put("author_id",1);
		when(bookService.addBook(any(Book.class))).thenReturn(true);

		// 发送请求并进行断言
		String expectedResponse = "";
		MvcResult mvcResult =mockMvc.perform((RequestBuilder) post("/createBook")
						.contentType(MediaType.APPLICATION_JSON)
						.queryParam( new Gson().toJson(createBookData)))
				.andExpect(status().isOk())
				.andReturn();
		String content = mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8);
		// 通过断言来验证返回是否正确
		Assertions.assertEquals(content, "create succeed");
	}

	@Test
	public void testDeleteBook() throws Exception {
		int bookIdToDelete = 1;
		when(bookService.deleteBook(bookIdToDelete)).thenReturn(true);

		String expectedResponse = "";
		MvcResult mvcResult =mockMvc.perform(get("/DeleteBook")
						.param("book_id", String.valueOf(bookIdToDelete)))
				.andExpect(status().isOk())
				.andReturn();
		String content = mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8);
		// 通过断言来验证返回是否正确
		Assertions.assertEquals(content, "delete succeed");
	}

	@Test
	public void testUpdateBook() throws Exception {
		Map<String, Object> bookData = new HashMap<>();
		bookData.put("book_name", "更新后的书名");
		bookData.put("brief_introduction", "更新后的简介");
		bookData.put("tag", "更新后的标签");
		bookData.put("image", "更新后的图片");
		bookData.put("book_id", 1);

		when(bookService.getBookById(1)).thenReturn(new Book());

		String expectedResponse = "";
		MvcResult mvcResult =mockMvc.perform((RequestBuilder) post("/UpdateBook")
						.contentType(MediaType.APPLICATION_JSON)
						.queryParam(new Gson().toJson(bookData)))
				.andExpect(status().isOk())
				.andReturn();

		String content = mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8);
		// 通过断言来验证返回是否正确
		Assertions.assertEquals(content, "update succeed");
	}

	@Test
	public void testGetBookICreate() throws Exception {
		int authorId = 1;
		List<Book> books = new ArrayList<>();
		// 添加一些模拟的书籍到列表中
		// 这里假设你已经正确实现了getBooksByAuthor方法
		when(bookService.getBooksByAuthor(authorId)).thenReturn(books);

		String expectedResponse = "";
		MvcResult mvcResult = mockMvc.perform(get("/GetBooksICreated")
						.param("open_id", String.valueOf(authorId)))
				.andExpect(status().isOk())
				.andReturn(); // 检查返回结果中是否包含"no books found"
		String content = mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8);
		// 通过断言来验证返回是否正确
		Assertions.assertEquals(content, "no books found");
	}

	@Test
	public void testPublishBook() throws Exception {
		int bookId = 1;
		when(bookService.getBookById(bookId)).thenReturn(new Book());

		String expectedResponse = "";
		MvcResult mvcResult =mockMvc.perform(get("/BookPublish")
						.param("book_id", String.valueOf(bookId) ) )
				.andExpect(status().isOk()) // 验证状态码是否为200
				.andReturn(); // 验证响应内容中是否包含"上架成功"
		String content = mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8);
		// 通过断言来验证返回是否正确
		Assertions.assertEquals(content, "Publish success");
	}

	@Test
	public void testPublishRemoveBook() throws Exception {
		int bookId = 1;
		when(bookService.getBookById(bookId)).thenReturn(new Book());


		String expectedResponse = "";
		MvcResult mvcResult = mockMvc.perform(get("/BookPublishRemove")
						.param("book_id", String.valueOf(bookId)))
				.andExpect(status().isOk())
				// 返回一个MvcResult回调对象
				.andReturn();

		String content = mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8);
		// 通过断言来验证返回是否正确
		Assertions.assertEquals(content, "remove successfully");


	}

}
