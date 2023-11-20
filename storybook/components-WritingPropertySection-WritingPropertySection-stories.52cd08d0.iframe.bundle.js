"use strict";(self.webpackChunkfrontend=self.webpackChunkfrontend||[]).push([[218],{"./src/components/WritingPropertySection/WritingPropertySection.stories.tsx":(__unused_webpack_module,__webpack_exports__,__webpack_require__)=>{__webpack_require__.r(__webpack_exports__),__webpack_require__.d(__webpack_exports__,{Primary:()=>Primary,__namedExportsOrder:()=>__namedExportsOrder,default:()=>__WEBPACK_DEFAULT_EXPORT__});var styles_storybook__WEBPACK_IMPORTED_MODULE_0__=__webpack_require__("./src/styles/storybook.ts"),_WritingPropertySection__WEBPACK_IMPORTED_MODULE_1__=__webpack_require__("./src/components/WritingPropertySection/WritingPropertySection.tsx"),react_jsx_runtime__WEBPACK_IMPORTED_MODULE_2__=__webpack_require__("./node_modules/react/jsx-runtime.js");const __WEBPACK_DEFAULT_EXPORT__={title:"publishing/WritingPropertySection",component:_WritingPropertySection__WEBPACK_IMPORTED_MODULE_1__.Z},Primary={render:()=>(0,react_jsx_runtime__WEBPACK_IMPORTED_MODULE_2__.jsx)(styles_storybook__WEBPACK_IMPORTED_MODULE_0__.y1,{children:(0,react_jsx_runtime__WEBPACK_IMPORTED_MODULE_2__.jsx)(_WritingPropertySection__WEBPACK_IMPORTED_MODULE_1__.Z,{writingId:200})})};Primary.parameters={...Primary.parameters,docs:{...Primary.parameters?.docs,source:{originalSource:"{\n  render: () => <StoryContainer>\n      <WritingPropertySection writingId={200} />\n    </StoryContainer>\n}",...Primary.parameters?.docs?.source}}};const __namedExportsOrder=["Primary"]},"./src/apis/fetch.ts":(__unused_webpack_module,__webpack_exports__,__webpack_require__)=>{__webpack_require__.d(__webpack_exports__,{d:()=>fetch_http});var distribution=__webpack_require__("./node_modules/ky/distribution/index.js"),HttpError=__webpack_require__("./src/utils/apis/HttpError.ts"),url=__webpack_require__("./src/constants/apis/url.ts");const fetch_http=distribution.ZP.create({hooks:{beforeError:[error=>{const{response,request,options}=error;return(0,HttpError.S)(response,request,options)}],beforeRequest:[request=>{request.headers.set("Authorization",`Bearer ${localStorage.getItem("accessToken")??""}`)}],afterResponse:[async(request,options,response)=>{if(401===response.status){const{error}=await response.json();if(4011===error.code){const newAccessToken=(await fetch_http.post(`${url.qW}/token/refresh`).json()).accessToken;return localStorage.setItem("accessToken",newAccessToken),request.headers.set("Authorization",`Bearer ${newAccessToken}`),fetch_http(request)}}}]}})},"./src/apis/writings.ts":(__unused_webpack_module,__webpack_exports__,__webpack_require__)=>{__webpack_require__.d(__webpack_exports__,{I3:()=>publishWritingToMedium,Jl:()=>getWriting,Yh:()=>updateWritingTitle,_H:()=>getWritingProperties,a0:()=>updateWritingOrder,aU:()=>publishWritingToTistory});var constants_apis_url__WEBPACK_IMPORTED_MODULE_0__=__webpack_require__("./src/constants/apis/url.ts"),_fetch__WEBPACK_IMPORTED_MODULE_1__=__webpack_require__("./src/apis/fetch.ts");const getWriting=writingId=>_fetch__WEBPACK_IMPORTED_MODULE_1__.d.get(`${constants_apis_url__WEBPACK_IMPORTED_MODULE_0__.lw}/${writingId}`).json(),getWritingProperties=writingId=>_fetch__WEBPACK_IMPORTED_MODULE_1__.d.get(`${constants_apis_url__WEBPACK_IMPORTED_MODULE_0__.lw}/${writingId}/properties`).json(),publishWritingToTistory=({writingId,body})=>_fetch__WEBPACK_IMPORTED_MODULE_1__.d.post(`${constants_apis_url__WEBPACK_IMPORTED_MODULE_0__.lw}/${writingId}/publish/tistory`,{json:body}),publishWritingToMedium=({writingId,body})=>_fetch__WEBPACK_IMPORTED_MODULE_1__.d.post(`${constants_apis_url__WEBPACK_IMPORTED_MODULE_0__.lw}/${writingId}/publish/medium`,{json:body}),updateWritingTitle=({writingId,body})=>_fetch__WEBPACK_IMPORTED_MODULE_1__.d.patch(`${constants_apis_url__WEBPACK_IMPORTED_MODULE_0__.lw}/${writingId}`,{json:body}),updateWritingOrder=({writingId,body})=>_fetch__WEBPACK_IMPORTED_MODULE_1__.d.patch(`${constants_apis_url__WEBPACK_IMPORTED_MODULE_0__.lw}/${writingId}`,{json:body})},"./src/components/@common/Tag/Tag.tsx":(__unused_webpack_module,__webpack_exports__,__webpack_require__)=>{__webpack_require__.d(__webpack_exports__,{Z:()=>__WEBPACK_DEFAULT_EXPORT__});var styled_components__WEBPACK_IMPORTED_MODULE_2__=__webpack_require__("./node_modules/styled-components/dist/styled-components.browser.esm.js"),assets_icons__WEBPACK_IMPORTED_MODULE_0__=__webpack_require__("./src/assets/icons/index.ts"),react_jsx_runtime__WEBPACK_IMPORTED_MODULE_1__=__webpack_require__("./node_modules/react/jsx-runtime.js");const Tag=({removable=!0,children,...rest})=>(0,react_jsx_runtime__WEBPACK_IMPORTED_MODULE_1__.jsxs)(S.Tag,{...rest,children:["#",children,removable&&(0,react_jsx_runtime__WEBPACK_IMPORTED_MODULE_1__.jsx)(assets_icons__WEBPACK_IMPORTED_MODULE_0__.Tw,{width:14,height:14})]});Tag.displayName="Tag";const __WEBPACK_DEFAULT_EXPORT__=Tag,S={Tag:styled_components__WEBPACK_IMPORTED_MODULE_2__.zo.button`
    display: inline-flex;
    align-items: center;
    padding: 0.6rem;
    background-color: ${({theme})=>theme.color.gray4};
    border-radius: 8px;
    color: ${({theme})=>theme.color.gray8};
    font-size: 1.2rem;
    font-weight: 600;
  `};try{Tag.displayName="Tag",Tag.__docgenInfo={description:"",displayName:"Tag",props:{removable:{defaultValue:{value:"true"},description:"",name:"removable",required:!1,type:{name:"boolean"}}}},"undefined"!=typeof STORYBOOK_REACT_CLASSES&&(STORYBOOK_REACT_CLASSES["src/components/@common/Tag/Tag.tsx#Tag"]={docgenInfo:Tag.__docgenInfo,name:"Tag",path:"src/components/@common/Tag/Tag.tsx#Tag"})}catch(__react_docgen_typescript_loader_error){}},"./src/components/WritingPropertySection/WritingPropertySection.tsx":(__unused_webpack_module,__webpack_exports__,__webpack_require__)=>{__webpack_require__.d(__webpack_exports__,{Z:()=>__WEBPACK_DEFAULT_EXPORT__});var react__WEBPACK_IMPORTED_MODULE_0__=__webpack_require__("./node_modules/react/index.js"),apis_writings__WEBPACK_IMPORTED_MODULE_1__=__webpack_require__("./src/apis/writings.ts"),assets_icons__WEBPACK_IMPORTED_MODULE_2__=__webpack_require__("./src/assets/icons/index.ts"),components_common_Tag_Tag__WEBPACK_IMPORTED_MODULE_3__=__webpack_require__("./src/components/@common/Tag/Tag.tsx"),styled_components__WEBPACK_IMPORTED_MODULE_8__=__webpack_require__("./node_modules/styled-components/dist/styled-components.browser.esm.js"),utils_date__WEBPACK_IMPORTED_MODULE_7__=__webpack_require__("./src/utils/date.ts"),_tanstack_react_query__WEBPACK_IMPORTED_MODULE_6__=__webpack_require__("./node_modules/@tanstack/react-query/build/lib/useQuery.mjs"),constants_blog__WEBPACK_IMPORTED_MODULE_4__=__webpack_require__("./src/constants/blog.tsx"),react_jsx_runtime__WEBPACK_IMPORTED_MODULE_5__=__webpack_require__("./node_modules/react/jsx-runtime.js");const WritingPropertySection=({writingId})=>{const{data:writingInfo}=(0,_tanstack_react_query__WEBPACK_IMPORTED_MODULE_6__.a)(["writingProperties",writingId],(()=>(0,apis_writings__WEBPACK_IMPORTED_MODULE_1__._H)(writingId)));return writingInfo?(0,react_jsx_runtime__WEBPACK_IMPORTED_MODULE_5__.jsxs)(S.WritingPropertySection,{children:[(0,react_jsx_runtime__WEBPACK_IMPORTED_MODULE_5__.jsx)(S.SectionTitle,{children:"정보"}),(0,react_jsx_runtime__WEBPACK_IMPORTED_MODULE_5__.jsxs)(S.InfoList,{children:[(0,react_jsx_runtime__WEBPACK_IMPORTED_MODULE_5__.jsxs)(S.Info,{children:[(0,react_jsx_runtime__WEBPACK_IMPORTED_MODULE_5__.jsx)(S.InfoTitle,{children:"글 정보"}),(0,react_jsx_runtime__WEBPACK_IMPORTED_MODULE_5__.jsx)(S.InfoContent,{children:(0,react_jsx_runtime__WEBPACK_IMPORTED_MODULE_5__.jsxs)(S.PropertyRow,{children:[(0,react_jsx_runtime__WEBPACK_IMPORTED_MODULE_5__.jsxs)(S.PropertyName,{children:[(0,react_jsx_runtime__WEBPACK_IMPORTED_MODULE_5__.jsx)(assets_icons__WEBPACK_IMPORTED_MODULE_2__.Qu,{width:12,height:12}),"생성 날짜"]}),(0,react_jsx_runtime__WEBPACK_IMPORTED_MODULE_5__.jsx)(S.PropertyValue,{children:(0,utils_date__WEBPACK_IMPORTED_MODULE_7__.v)(writingInfo.createdAt,"YYYY/MM/DD HH:MM")})]})})]}),Boolean(writingInfo.publishedDetails.length)&&(0,react_jsx_runtime__WEBPACK_IMPORTED_MODULE_5__.jsxs)(S.Info,{children:[(0,react_jsx_runtime__WEBPACK_IMPORTED_MODULE_5__.jsx)(S.InfoTitle,{children:"발행 정보"}),(0,react_jsx_runtime__WEBPACK_IMPORTED_MODULE_5__.jsx)(S.InfoContent,{children:writingInfo.publishedDetails.map((({blogName,publishedAt,tags,publishedUrl},index)=>(0,react_jsx_runtime__WEBPACK_IMPORTED_MODULE_5__.jsxs)(react__WEBPACK_IMPORTED_MODULE_0__.Fragment,{children:[(0,react_jsx_runtime__WEBPACK_IMPORTED_MODULE_5__.jsx)(S.PropertyRow,{children:(0,react_jsx_runtime__WEBPACK_IMPORTED_MODULE_5__.jsxs)(S.PropertyName,{children:[constants_blog__WEBPACK_IMPORTED_MODULE_4__.z2[blogName]," ",constants_blog__WEBPACK_IMPORTED_MODULE_4__.Wd[blogName]]})}),publishedUrl&&(0,react_jsx_runtime__WEBPACK_IMPORTED_MODULE_5__.jsxs)(S.PropertyRow,{children:[(0,react_jsx_runtime__WEBPACK_IMPORTED_MODULE_5__.jsxs)(S.PropertyName,{children:[(0,react_jsx_runtime__WEBPACK_IMPORTED_MODULE_5__.jsx)(assets_icons__WEBPACK_IMPORTED_MODULE_2__.yf,{width:10,height:10}),"발행 링크"]}),(0,react_jsx_runtime__WEBPACK_IMPORTED_MODULE_5__.jsx)(S.PropertyValue,{children:(0,react_jsx_runtime__WEBPACK_IMPORTED_MODULE_5__.jsx)(S.BlogLink,{href:publishedUrl,target:"_blank",rel:"external",children:"블로그로 이동하기"})})]}),(0,react_jsx_runtime__WEBPACK_IMPORTED_MODULE_5__.jsxs)(S.PropertyRow,{children:[(0,react_jsx_runtime__WEBPACK_IMPORTED_MODULE_5__.jsxs)(S.PropertyName,{children:[(0,react_jsx_runtime__WEBPACK_IMPORTED_MODULE_5__.jsx)(assets_icons__WEBPACK_IMPORTED_MODULE_2__.Qu,{width:12,height:12}),"발행일"]}),(0,react_jsx_runtime__WEBPACK_IMPORTED_MODULE_5__.jsx)(S.PropertyValue,{children:(0,utils_date__WEBPACK_IMPORTED_MODULE_7__.v)(publishedAt,"YYYY/MM/DD HH:MM")})]}),(0,react_jsx_runtime__WEBPACK_IMPORTED_MODULE_5__.jsxs)(S.PropertyRow,{children:[(0,react_jsx_runtime__WEBPACK_IMPORTED_MODULE_5__.jsxs)(S.PropertyName,{children:[(0,react_jsx_runtime__WEBPACK_IMPORTED_MODULE_5__.jsx)(assets_icons__WEBPACK_IMPORTED_MODULE_2__.lO,{width:12,height:12}),"태그"]}),(0,react_jsx_runtime__WEBPACK_IMPORTED_MODULE_5__.jsx)(S.PropertyValue,{children:tags.map((tag=>(0,react_jsx_runtime__WEBPACK_IMPORTED_MODULE_5__.jsx)(components_common_Tag_Tag__WEBPACK_IMPORTED_MODULE_3__.Z,{removable:!1,children:tag},tag)))})]}),(0,react_jsx_runtime__WEBPACK_IMPORTED_MODULE_5__.jsx)(S.Spacer,{})]},index)))})]})]})]}):null};WritingPropertySection.displayName="WritingPropertySection";const __WEBPACK_DEFAULT_EXPORT__=WritingPropertySection,S={WritingPropertySection:styled_components__WEBPACK_IMPORTED_MODULE_8__.zo.section`
    display: flex;
    flex-direction: column;
    gap: 2rem;
  `,SectionTitle:styled_components__WEBPACK_IMPORTED_MODULE_8__.zo.h1`
    display: flex;
    gap: 1.5rem;
    font-size: 1.5rem;
    font-weight: 700;
    line-height: 1.5rem;
  `,InfoList:styled_components__WEBPACK_IMPORTED_MODULE_8__.zo.div`
    display: flex;
    flex-direction: column;
    gap: 1rem;
  `,Info:styled_components__WEBPACK_IMPORTED_MODULE_8__.zo.div`
    color: ${({theme})=>theme.color.gray7};
  `,InfoTitle:styled_components__WEBPACK_IMPORTED_MODULE_8__.zo.h2`
    color: ${({theme})=>theme.color.gray8};
    font-size: 1.3rem;
    font-weight: 600;
    line-height: 1.3rem;
  `,InfoContent:styled_components__WEBPACK_IMPORTED_MODULE_8__.zo.div`
    display: flex;
    flex-direction: column;
    gap: 1.8rem;
    padding: 1.6rem 0.9rem;
    font-size: 1.3rem;
    line-height: 1.3rem;
  `,PropertyRow:styled_components__WEBPACK_IMPORTED_MODULE_8__.zo.div`
    display: flex;
    align-items: center;
  `,PropertyName:styled_components__WEBPACK_IMPORTED_MODULE_8__.zo.div`
    display: flex;
    align-items: center;
    flex-shrink: 0;
    gap: 0.4rem;
    width: 9.5rem;
    color: ${({theme})=>theme.color.gray8};
    font-size: 1.3rem;
    font-weight: 600;

    svg {
      width: 1.2rem;
      height: 1.2rem;
    }
  `,PropertyValue:styled_components__WEBPACK_IMPORTED_MODULE_8__.zo.div`
    color: ${({theme})=>theme.color.gray10};
    display: flex;
    flex-wrap: wrap;
    gap: 0.2rem;
  `,Spacer:styled_components__WEBPACK_IMPORTED_MODULE_8__.zo.div`
    height: 0.8rem;
  `,BlogLink:styled_components__WEBPACK_IMPORTED_MODULE_8__.zo.a`
    color: ${({theme})=>theme.color.gray12};
    font-weight: 500;
  `};try{WritingPropertySection.displayName="WritingPropertySection",WritingPropertySection.__docgenInfo={description:"",displayName:"WritingPropertySection",props:{writingId:{defaultValue:null,description:"",name:"writingId",required:!0,type:{name:"number"}}}},"undefined"!=typeof STORYBOOK_REACT_CLASSES&&(STORYBOOK_REACT_CLASSES["src/components/WritingPropertySection/WritingPropertySection.tsx#WritingPropertySection"]={docgenInfo:WritingPropertySection.__docgenInfo,name:"WritingPropertySection",path:"src/components/WritingPropertySection/WritingPropertySection.tsx#WritingPropertySection"})}catch(__react_docgen_typescript_loader_error){}},"./src/constants/blog.tsx":(__unused_webpack_module,__webpack_exports__,__webpack_require__)=>{__webpack_require__.d(__webpack_exports__,{N1:()=>BLOG_LIST,Wd:()=>BLOG_KOREAN,z2:()=>BLOG_ICON});var assets_icons__WEBPACK_IMPORTED_MODULE_0__=__webpack_require__("./src/assets/icons/index.ts"),react_jsx_runtime__WEBPACK_IMPORTED_MODULE_1__=__webpack_require__("./node_modules/react/jsx-runtime.js");const BLOG_LIST={MEDIUM:"MEDIUM",TISTORY:"TISTORY"},BLOG_KOREAN={MEDIUM:"미디엄",TISTORY:"티스토리"},BLOG_ICON={MEDIUM:(0,react_jsx_runtime__WEBPACK_IMPORTED_MODULE_1__.jsx)(assets_icons__WEBPACK_IMPORTED_MODULE_0__.Xx,{}),TISTORY:(0,react_jsx_runtime__WEBPACK_IMPORTED_MODULE_1__.jsx)(assets_icons__WEBPACK_IMPORTED_MODULE_0__.hr,{})}},"./src/styles/storybook.ts":(__unused_webpack_module,__webpack_exports__,__webpack_require__)=>{__webpack_require__.d(__webpack_exports__,{CA:()=>StoryItemContainer,VP:()=>StoryItemTitle,y1:()=>StoryContainer});var styled_components__WEBPACK_IMPORTED_MODULE_0__=__webpack_require__("./node_modules/styled-components/dist/styled-components.browser.esm.js");const StoryContainer=styled_components__WEBPACK_IMPORTED_MODULE_0__.zo.div`
  display: flex;
  flex-direction: column;
  width: 300px;
  gap: 28px;
`,StoryItemContainer=styled_components__WEBPACK_IMPORTED_MODULE_0__.zo.div`
  display: flex;
  flex-direction: column;
  align-items: flex-start;
  gap: 12px;
`,StoryItemTitle=(styled_components__WEBPACK_IMPORTED_MODULE_0__.zo.div`
  display: flex;
  flex-direction: row;
  gap: 12px;
`,styled_components__WEBPACK_IMPORTED_MODULE_0__.zo.h3`
  color: ${({theme})=>theme.color.gray9};
  font-size: 12px;
  font-weight: 400;
  text-transform: uppercase;
`)},"./src/utils/date.ts":(__unused_webpack_module,__webpack_exports__,__webpack_require__)=>{__webpack_require__.d(__webpack_exports__,{v:()=>dateFormatter});const dateFormatter=(date,format)=>{switch(format){case"YYYY-MM-DD":return new Intl.DateTimeFormat("en-CA",{year:"numeric",month:"2-digit",day:"2-digit"}).format(date);case"YYYY.MM.DD.":return new Intl.DateTimeFormat("ko-KR").format(new Date(date));case"YYYY/MM/DD HH:MM":const d=new Date(date);return`${d.getFullYear()}/${String(d.getMonth()+1).padStart(2,"0")}/${String(d.getDate()).padStart(2,"0")} ${String(d.getHours()).padStart(2,"0")}:${String(d.getMinutes()).padStart(2,"0")}`;case"HH:MM":const today=new Date(date);return`${String(today.getHours()).padStart(2,"0")}:${String(today.getMinutes()).padStart(2,"0")}`}}}}]);
//# sourceMappingURL=components-WritingPropertySection-WritingPropertySection-stories.52cd08d0.iframe.bundle.js.map