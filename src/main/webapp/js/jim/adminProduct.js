// 初始化
let pageButton = document.getElementsByClassName("pageButton")[0];
(function init() {
  getProductList();
})();
// 透過AJAX取得頁面資料
function getProductList(page, count) {
  axios
    .get(`${root}/mall/productList`, {
      params: {
        page: page,
        count: count,
      },
    })
    .then((response) => {
      renderProduct(response.data.content);
      renderPageButton(response.data);
    });
}

function renderProduct(pList) {
  let outputString = "";
  for (let p of pList) {
    const dateString = `${p.addedTime}`;
    const date = new Date(dateString);
    const options = { year: "numeric", month: "2-digit", day: "2-digit" };
    const formattedDate = date.toLocaleString("zh-TW", options);

    outputString += "<tr>";
    outputString += `<td>${p.productName}</td>`;
    outputString += `<td>${p.productPrice}元</td>`;
    outputString += "<td>" + formattedDate + "</td>";
    outputString += `<td>${p.productDescription}</td>`;
    outputString += `<td>${p.productPlayTime}</td>`;
    outputString += `<td>${p.productMinPlayer}~${p.productMaxPlayer}</td>`;
    outputString += `<td>${p.productDifficulty}</td>`;
    outputString += "<td>";
    outputString += `<button class="deleteButton btn btn-danger btn-circle" value="${p.productId}">`;
    outputString += "<i class='fas fa-trash'></i>";
    outputString += "</button>";
    outputString += "</td>";
    outputString += "<td>";
    outputString += `<form action='${root}/mall/updateProduct' method='GET'>`;
    outputString += `<button class="updateButton btn btn-info btn-circle" type='submit'>`;
    outputString += "<i class='fas fa-info-circle'></i>";
    outputString += "</button>";
    outputString += `<input type='hidden' name='id' value='${p.productId}'>`;
    outputString += "</form>";
    outputString += "</td>";
    outputString += "</tr>";
  }
  $("#dataHome").html(outputString);

  // 用AJAX透過ID刪除資料
  let deleteButton = document.getElementsByClassName("deleteButton");
  for (i = 0; i < deleteButton.length; i++) {
    deleteButton[i].addEventListener("click", function () {
      let pId = this.value;
      axios
        .delete(`${root}/mall/deleteProductById`, {
          params: { id: pId },
        })
        .then((response) => {
          getProductList();
        })
        .catch((error) => console.log(error));
    });
  }
}

// 渲染分頁按鈕
function renderPageButton(pageInfo) {
  let outputString = "";
  outputString += `<ul class="pagination justify-content-center">`;
  let disabled = pageInfo.number == 0 ? "disabled" : "";
  outputString += `<li class="page-item ${disabled}">`;
  outputString += `<button class="page-link">＜</button>`;
  outputString += `</li>`;

  for (let i = 1; i <= pageInfo.totalPages; i++) {
    let active = i == pageInfo.number + 1 ? "active" : "";
    outputString += `<li class="page-item ${active}"><button class="page-link">${i}</button></li>`;
  }

  disabled = pageInfo.number == pageInfo.totalPages - 1 ? "disabled" : "";
  outputString += `<li class="page-item ${disabled}">`;

  outputString += `<button class="page-link">＞</button>`;

  outputString += `</li>`;
  outputString += `</ul>`;
  pageButton.innerHTML = outputString;
}

// 分頁點擊換頁
pageButton.addEventListener("click", function (e) {
  if (e.target.tagName != "BUTTON") {
    return;
  }
  let page = e.target.innerText;
  page =
    page == "＜"
      ? parseInt(pageButton.getElementsByClassName("active")[0].innerText) - 1
      : page;
  page =
    page == "＞"
      ? parseInt(pageButton.getElementsByClassName("active")[0].innerText) + 1
      : page;
  getProductList(page, 6);
});
