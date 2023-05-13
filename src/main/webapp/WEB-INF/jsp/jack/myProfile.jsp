<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
        <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
            <%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
                <%@ include file="../include/common_link.jsp" %>
                    <!DOCTYPE html>
                    <html>

                    <head>
                        <meta charset="UTF-8">
                        <title>${webName}</title>

                        <link rel="stylesheet" type="text/css" href="${root}/css/member/otherMember.css">
                        <style>
                            .container {
                                padding-top: 80px;
                            }

                            /* div.list-group{
								position: absolute;
								z-index: 1;
							} */

                            
                        </style>
                    </head>

                    <body>
                        <jsp:include page="../include/header.jsp"></jsp:include>


                        <div class="container">
                            <!-- Topbar Search -->
                            <div
                                class="d-none d-sm-inline-block form-inline mr-auto ml-md-3 my-2 my-md-0 mw-100 p-3 py-5">
                                <div class="input-group">
                                    <div class="input-group-append">
                                        <button class="btn btn-warning" type="button" style="border-radius: 5px">
                                            <i class="fas fa-search fa-sm"></i>
                                        </button>
                                    </div>
                                    <input type="text" class="form-control bg-light border-0 small" placeholder="搜尋會員"
                                        aria-label="Search" aria-describedby="basic-addon2" id="searchMember"
                                        style="width: 400px; border-radius: 5px">
                                </div>
                                <div class="list-group" id="result"
                                    style="width: 400px; border-radius: 5px; margin-left:auto;">
                                    
                                </div>
                            </div>
                            <!-- Topbar Search -->

                            <div class="profile">
                                <div class="profile-header">
                                    <div class="profile-header-cover"></div>
                                    <div class="profile-header-content">
                                        <div class="profile-header-img">
                                            <img src="${root}/member/findMemberImg/${member.memberId}" alt="" />
                                        </div>
                                        <ul class="profile-header-tab nav nav-tabs nav-tabs-v2">
                                            <li class="nav-item">
                                                <a href="#profile-post" class="nav-link" data-toggle="tab">
                                                    <div class="nav-field">Posts</div>
                                                    <div class="nav-value">382</div>
                                                </a>
                                            </li>
                                            <li class="nav-item">
                                                <a href="#profile-followers" class="nav-link active" data-toggle="tab">
                                                    <div class="nav-field">Followers</div>
                                                    <div class="nav-value">1.3m</div>
                                                </a>
                                            </li>
                                            <li class="nav-item">
                                                <a href="#profile-media" class="nav-link" data-toggle="tab">
                                                    <div class="nav-field">Photos</div>
                                                    <div class="nav-value">1,397</div>
                                                </a>
                                            </li>
                                            <li class="nav-item">
                                                <a href="#profile-media" class="nav-link" data-toggle="tab">
                                                    <div class="nav-field">Videos</div>
                                                    <div class="nav-value">120</div>
                                                </a>
                                            </li>
                                            <li class="nav-item">
                                                <a href="#profile-followers" class="nav-link" data-toggle="tab">
                                                    <div class="nav-field">Following</div>
                                                    <div class="nav-value">2,592</div>
                                                </a>
                                            </li>
                                        </ul>
                                    </div>
                                </div>

                                <div class="profile-container">
                                    <div class="profile-sidebar">
                                        <div class="desktop-sticky-top">
                                            <h4>${member.memberName}</h4>
                                            <div class="font-weight-600 mb-3 text-muted mt-n2">${member.memberEmail}</div>
                                            <p>
                                                
                                            </p>
                                            <div class="mb-1"><i class="fa fa-map-marker-alt fa-fw text-muted"></i> ${member.memberAddress}</div>
                                            <div class="mb-3"><i class="fa fa-link fa-fw text-muted"></i>
                                                seantheme.com/studio</div>
                                            <hr class="mt-4 mb-4" />
                                        </div>
                                    </div>

                                    <div class="profile-content">
                                        <div class="row">
                                            <div class="col-xl-12">
                                                <div class="tab-content p-0">
                                                    <div class="tab-pane fade active show" id="profile-followers">
                                                        <div class="list-group">
                                                            <div class="list-group-item d-flex align-items-center">
                                                                <img src="https://bootdey.com/img/Content/avatar/avatar1.png"
                                                                    alt="" width="50px" class="rounded-sm ml-n2" />
                                                                <div class="flex-fill pl-3 pr-3">
                                                                    <div><a href="#"
                                                                            class="text-dark font-weight-600">Ethel
                                                                            Wilkes</a></div>
                                                                    <div class="text-muted fs-13px">North Raundspic
                                                                    </div>
                                                                </div>
                                                                <a href="#" class="btn btn-outline-primary">Follow</a>
                                                            </div>
                                                            <div class="list-group-item d-flex align-items-center">
                                                                <img src="https://bootdey.com/img/Content/avatar/avatar2.png"
                                                                    alt="" width="50px" class="rounded-sm ml-n2" />
                                                                <div class="flex-fill pl-3 pr-3">
                                                                    <div><a href="#"
                                                                            class="text-dark font-weight-600">Shanaya
                                                                            Hansen</a></div>
                                                                    <div class="text-muted fs-13px">North Raundspic
                                                                    </div>
                                                                </div>
                                                                <a href="#" class="btn btn-outline-primary">Follow</a>
                                                            </div>
                                                            <div class="list-group-item d-flex align-items-center">
                                                                <img src="https://bootdey.com/img/Content/avatar/avatar3.png"
                                                                    alt="" width="50px" class="rounded-sm ml-n2" />
                                                                <div class="flex-fill pl-3 pr-3">
                                                                    <div><a href="#"
                                                                            class="text-dark font-weight-600">James
                                                                            Allman</a></div>
                                                                    <div class="text-muted fs-13px">North Raundspic
                                                                    </div>
                                                                </div>
                                                                <a href="#" class="btn btn-outline-primary">Follow</a>
                                                            </div>
                                                            <div class="list-group-item d-flex align-items-center">
                                                                <img src="https://bootdey.com/img/Content/avatar/avatar4.png"
                                                                    alt="" width="50px" class="rounded-sm ml-n2" />
                                                                <div class="flex-fill pl-3 pr-3">
                                                                    <div><a href="#"
                                                                            class="text-dark font-weight-600">Marie
                                                                            Welsh</a></div>
                                                                    <div class="text-muted fs-13px">Crencheporford</div>
                                                                </div>
                                                                <a href="#" class="btn btn-outline-primary">Follow</a>
                                                            </div>
                                                            <div class="list-group-item d-flex align-items-center">
                                                                <img src="https://bootdey.com/img/Content/avatar/avatar5.png"
                                                                    alt="" width="50px" class="rounded-sm ml-n2" />
                                                                <div class="flex-fill pl-3 pr-3">
                                                                    <div><a href="#"
                                                                            class="text-dark font-weight-600">Lamar
                                                                            Kirkland</a></div>
                                                                    <div class="text-muted fs-13px">Prince Ewoodswan
                                                                    </div>
                                                                </div>
                                                                <a href="#" class="btn btn-outline-primary">Follow</a>
                                                            </div>
                                                            <div class="list-group-item d-flex align-items-center">
                                                                <img src="https://bootdey.com/img/Content/avatar/avatar6.png"
                                                                    alt="" width="50px" class="rounded-sm ml-n2" />
                                                                <div class="flex-fill pl-3 pr-3">
                                                                    <div><a href="#"
                                                                            class="text-dark font-weight-600">Bentley
                                                                            Osborne</a></div>
                                                                    <div class="text-muted fs-13px">Red Suvern</div>
                                                                </div>
                                                                <a href="#" class="btn btn-outline-primary">Follow</a>
                                                            </div>
                                                            <div class="list-group-item d-flex align-items-center">
                                                                <img src="https://bootdey.com/img/Content/avatar/avatar7.png"
                                                                    alt="" width="50px" class="rounded-sm ml-n2" />
                                                                <div class="flex-fill pl-3 pr-3">
                                                                    <div><a href="#"
                                                                            class="text-dark font-weight-600">Ollie
                                                                            Goulding</a></div>
                                                                    <div class="text-muted fs-13px">Doa</div>
                                                                </div>
                                                                <a href="#" class="btn btn-outline-primary">Follow</a>
                                                            </div>
                                                            <div class="list-group-item d-flex align-items-center">
                                                                <img src="https://bootdey.com/img/Content/avatar/avatar8.png"
                                                                    alt="" width="50px" class="rounded-sm ml-n2" />
                                                                <div class="flex-fill pl-3 pr-3">
                                                                    <div><a href="#"
                                                                            class="text-dark font-weight-600">Hiba
                                                                            Calvert</a></div>
                                                                    <div class="text-muted fs-13px">Stemunds</div>
                                                                </div>
                                                                <a href="#" class="btn btn-outline-primary">Follow</a>
                                                            </div>
                                                            <div class="list-group-item d-flex align-items-center">
                                                                <img src="https://bootdey.com/img/Content/avatar/avatar3.png"
                                                                    alt="" width="50px" class="rounded-sm ml-n2" />
                                                                <div class="flex-fill pl-3 pr-3">
                                                                    <div><a href="#"
                                                                            class="text-dark font-weight-600">Rivka
                                                                            Redfern</a></div>
                                                                    <div class="text-muted fs-13px">Fallnee</div>
                                                                </div>
                                                                <a href="#" class="btn btn-outline-primary">Follow</a>
                                                            </div>
                                                            <div class="list-group-item d-flex align-items-center">
                                                                <img src="https://bootdey.com/img/Content/avatar/avatar1.png"
                                                                    alt="" width="50px" class="rounded-sm ml-n2" />
                                                                <div class="flex-fill pl-3 pr-3">
                                                                    <div><a href="#"
                                                                            class="text-dark font-weight-600">Roshni
                                                                            Fernandez</a></div>
                                                                    <div class="text-muted fs-13px">Mount Lerdo</div>
                                                                </div>
                                                                <a href="#" class="btn btn-outline-primary">Follow</a>
                                                            </div>
                                                        </div>
                                                        <div class="text-center p-3">
                                                            <a href="#" class="text-dark text-decoration-none">Show more
                                                                <b class="caret"></b></a>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>



                        <jsp:include page="../include/footer.jsp"></jsp:include>

                        <script type="text/javascript" src="${root}/js/jack/myProfile.js"></script>
                    </body>

                    </html>