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
<link rel="stylesheet" type="text/css" href="${root}/css/card/cardReleased.css">
<link rel="stylesheet" type="text/css" href="${root}/css/card/cardList.css">
<link rel="stylesheet" type="text/css" href="${root}/css/card/mycard.css">
</head>
<body>

    <jsp:include page="../include/header.jsp"></jsp:include>
    <div class="releasedContainer">
        <div class="releasedTitle">交易市集</div>
            <div class="releasedSelect">
                <button></button>

            </div>
        <div class="row container cardContainer justify-content-center" id="cardContainer">





        </div>
        
    </div>
    <jsp:include page="../include/footer.jsp"></jsp:include>

    <script>

        $(async function(){
            await getAllList();
        })

        function getAllList() {
            return axios.get("${root}/released/all")
            .then(res => {
                let htmlstr = "";
                
                let onwedIdArray = [];

                for (i = 0; i < res.data.cardList.length; i++) {
                    // console.log(res.data.cardList[i])
                    onwedIdArray.push($.get(`${root}/released/owned/\${res.data.cardList[i].fkOwnedId}`))    
                }

                let cardArray = [];

                Promise.all(onwedIdArray).then(function(ownedRes) {
                    
                    for(i = 0; i < ownedRes.length; i++) {
                        // console.log(ownedRes[i].showOnwedDetail)
                        cardArray.push($.get(`${root}/released/getCard/\${ownedRes[i].showOnwedDetail.fkCardId}`))
                    }

                    let cardDetailImg = [];
                    let cardName = [];
                    
                    Promise.all(cardArray).then(function(cardRes) {
                        
                        for (i = 0; i < cardRes.length; i++) {
                            cardDetailImg.push(`${root}/card/downloadCard/\${cardRes[i].card.cardId}`)
                            cardName.push(`\${cardRes[i].card.cardName}`)
                            console.log(cardRes[i].card.cardName)
                        }
                        
                        Promise.all(cardDetailImg).then(function(results){

                            console.log(cardName)
                            for (i = 0; i < cardName.length; i++) {
                                htmlstr += `<div class="col-3 d-flex"><div class="card">`;
                                htmlstr += `<figure><img alt="" src="\${results[i]}" class="hanafuda">`;
                                htmlstr += `<figcaption>\${cardName[i]}</figcaption>`;
                                htmlstr += `</figure></div></div>`;
                            }

                            
                            console.log("htmlstr" + htmlstr);
                            $("#cardContainer").append(htmlstr);

                        })
                    })
                })
                
                
            })
            .catch(err => {
                console.error(err); 
            })
        }

        

    </script>

</body>
</html>