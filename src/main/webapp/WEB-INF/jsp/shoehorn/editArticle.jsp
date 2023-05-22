<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ include file="../include/common_link.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>${webName}</title>
<link rel="stylesheet" type="text/css" href="${root}/css/index.css" />

<style>

	.articleBody{
		width:60%;
		margin:auto;
		text-align:center;
	}
	


  table {
    width: 100%;
    border-collapse: collapse;
  }
  td {
    padding: 5px;
  }
  label {
    font-size: 25px;
    display: inline-block;
    text-align: right;
    margin-right: 10px;
  }
  input[type="text"],
  textarea {
    font-size: 30px;
    width: 100%;
    box-sizing: border-box;
  }
  .center-text {
    text-align: center;
  }
  .display-value {
    font-size: 25px;
    vertical-align: middle;
  }
  button{
  	font-size: 20px;
  }

</style>


</head>
<!-- 最上面那排bar -->
<jsp:include page="../include/header.jsp" />
<body>
	<div class="bodyContainer">

		<!-- 顯示文章 -->
		<div class="articleBody">


<form action="${root}/edit" method="post">
  <table>
    <tr>
      <td><label for="product">遊戲:</label></td>
      <td class="display-value">${productName}</td>
      <td><label for="createdDate">發文日期:</label></td>
      <td class="display-value"><fmt:formatDate type="both" dateStyle="short" timeStyle="short"  
value="${article.articleCreatedDate}" /></td>
      <td><label for="title">標題:</label></td>
      <td><input type="text" id="title" name="title" value="${article.articleTitle}"></td>
    </tr>
    <tr>
      <td colspan="6" class="center-text">
        <label for="content">內容編輯:</label>
        <br>
        <textarea id="editor" name="content">${article.articleContent}</textarea>
      </td>
    </tr>
    <tr>
      <td colspan="6" style="text-align: center;">
        <input type="text" id="articleId" name="articleId" value="${article.articleId}" style="display: none;">
        <input type="text" id="poster" name="poster" value="${article.fkMemberId}" style="display: none;">
        <button type="submit">修改</button>
      </td>
    </tr>
  </table>
</form>





		</div>

	</div>
</body>
<!-- 最下面標題 -->
<jsp:include page="../include/footer.jsp" />

<!-- CK Editor -->
	<script
		src="https://cdn.ckeditor.com/ckeditor5/37.1.0/super-build/ckeditor.js"></script>

	<script
		src="https://cdn.ckeditor.com/ckeditor5/37.1.0/super-build/translations/zh.js"></script>

	<script>
            CKEDITOR.ClassicEditor.create(document.getElementById("editor"), {
                toolbar: {
                    items: [
                        
                        'heading', '|',
                        'bold', 'italic', 'strikethrough', 'underline', '|',
                        'fontSize', 'fontFamily', 'fontColor', 'fontBackgroundColor', '|',
                        'bulletedList', 'numberedList', 'todoList', '|',
                        'outdent', 'indent', '|',
                        'alignment', '|',
                        '-',
                        'findAndReplace', 'undo', 'redo', '|',
                        'link', 'insertImage', 'blockQuote', 'insertTable', '|',
                        'specialCharacters', 'horizontalLine', '|'
                    ],
                    shouldNotGroupWhenFull: true
                },
                language: 'zh',
                list: {
                    properties: {
                        styles: true,
                        startIndex: true,
                        reversed: true
                    }
                },
                heading: {
                    options: [
                        { model: 'paragraph', title: 'Paragraph', class: 'ck-heading_paragraph' },
                        { model: 'heading1', view: 'h1', title: 'Heading 1', class: 'ck-heading_heading1' },
                        { model: 'heading2', view: 'h2', title: 'Heading 2', class: 'ck-heading_heading2' },
                        { model: 'heading3', view: 'h3', title: 'Heading 3', class: 'ck-heading_heading3' },
                        { model: 'heading4', view: 'h4', title: 'Heading 4', class: 'ck-heading_heading4' },
                        { model: 'heading5', view: 'h5', title: 'Heading 5', class: 'ck-heading_heading5' },
                        { model: 'heading6', view: 'h6', title: 'Heading 6', class: 'ck-heading_heading6' }
                    ]
                },
                placeholder: '請輸入文章',
                fontFamily: {
                    options: [
                        'default',
                        'Arial, Helvetica, sans-serif',
                        'Courier New, Courier, monospace',
                        'Georgia, serif',
                        'Lucida Sans Unicode, Lucida Grande, sans-serif',
                        'Tahoma, Geneva, sans-serif',
                        'Times New Roman, Times, serif',
                        'Trebuchet MS, Helvetica, sans-serif',
                        'Verdana, Geneva, sans-serif'
                    ],
                    supportAllValues: true
                },
                fontSize: {
                    options: [ 10, 12, 14, 'default', 18, 20, 22 ],
                    supportAllValues: true
                },
                htmlSupport: {
                    allow: [
                        {
                            name: /.*/,
                            attributes: true,
                            classes: true,
                            styles: true
                        }
                    ]
                },
                htmlEmbed: {
                    showPreviews: true
                },
                link: {
                    decorators: {
                        addTargetToExternalLinks: true,
                        defaultProtocol: 'https://',
                        toggleDownloadable: {
                            mode: 'manual',
                            label: 'Downloadable',
                            attributes: {
                                download: 'file'
                            }
                        }
                    }
                },
                removePlugins: [
                    'CKBox',
                    'CKFinder',
                    'EasyImage',
                    'RealTimeCollaborativeComments',
                    'RealTimeCollaborativeTrackChanges',
                    'RealTimeCollaborativeRevisionHistory',
                    'PresenceList',
                    'Comments',
                    'TrackChanges',
                    'TrackChangesData',
                    'RevisionHistory',
                    'Pagination',
                    'WProofreader',
                    'MathType',
                    'SlashCommand',
                    'Template',
                    'DocumentOutline',
                    'FormatPainter',
                    'TableOfContents'
                ]
            });
        </script>

</html>