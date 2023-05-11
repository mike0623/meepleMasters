// 初始化
let pageButton = document.getElementsByClassName("pageButton")[0];
(function init() {
  getProductList();
})();
// 透過AJAX取得頁面資料
function getProductList(page = 1, count = 6) {
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

// 渲染商品列表
function renderProduct(pList) {
  let outputString = "";
  for (let p of pList) {
    outputString += '<div class="col-4 d-flex align-items-stretch">';
    outputString += '<div class="card">';
    outputString += '<div class="pic">';
    outputString += `<img src="${root}/mall/getPhoto?pId=${p.productId}">`;
    outputString += "</div>";
    outputString += `<div class="card-header">${p.productName}</div>`;
    outputString += '<div class="card-body">';
    outputString += `<h3 class="title">${p.productPrice}元</h3>`;
    outputString += "<ul>";
    outputString += `<li>${p.addedTime}</li>`;
    outputString += `<li>${p.productDescription}</li>`;
    outputString += `<li>${p.productPlayTime}</li>`;
    outputString += `<li>${p.productMinPlayer}~${p.productMaxPlayer}</li>`;
    outputString += `<li>${p.productDifficulty}</li>`;
    outputString += "</ul>";
    outputString += "</div>";
    outputString += '<div class="card-footer">';
    outputString += '<div class="text">';
    outputString += "<ul>";
    outputString += "<li>";
    outputString += `<button class="cartButton" value="${p.productId}">加入購物車</button>`;
    outputString += "</li>";
    outputString += "<li>";
    outputString += `<button class="faviroteButton" value="${p.productId}">加入最愛</button>`;
    outputString += "</li>";

    outputString += "</ul></div></div></div></div>";
  }
  $("#dataHome").html(outputString);

  addCartButton();
  addFaviroteButton();
}

// 用AJAX將商品加入購物車
function addCartButton() {
  let cartButton = document.getElementsByClassName("cartButton");
  for (i = 0; i < cartButton.length; i++) {
    cartButton[i].addEventListener("click", function () {
      let pId = this.value;
      if (mId == "") {
        alert("請先登入");
        window.location.replace(`${root}/login`);
        return;
      }
      axios
        .get(`${root}/shoppingCart/addShoppingCart`, {
          params: {
            productId: pId,
            memberId: mId,
          },
        })
        .then((response) => {
          if (response.data == "join") {
            alert("加入購物車");
          }
          if (response.data == "remove") {
            alert("刪除購物車");
          }
        })
        .catch((error) => console.log(error));
    });
  }
}

// 用AJAX將商品加入最愛
function addFaviroteButton() {
  let faviroteButton = document.getElementsByClassName("faviroteButton");
  for (i = 0; i < faviroteButton.length; i++) {
    faviroteButton[i].addEventListener("click", function () {
      let pId = this.value;
      if (mId == "") {
        alert("請先登入");
        window.location.replace(`${root}/login`);
        return;
      }
      axios
        .get(`${root}/favoriteGame/addFavoriteGame`, {
          params: {
            productId: pId,
            memberId: mId,
          },
        })
        .then((response) => {
          if (response.data == "like") {
            alert("新增最愛");
          }
          if (response.data == "dislike") {
            alert("刪除最愛");
          }
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
