"use strict";(self.webpackChunkfrontend=self.webpackChunkfrontend||[]).push([[882],{"./src/components/WritingTable/WritingTable.stories.tsx":(__unused_webpack_module,__webpack_exports__,__webpack_require__)=>{__webpack_require__.r(__webpack_exports__),__webpack_require__.d(__webpack_exports__,{Playground:()=>Playground,__namedExportsOrder:()=>__namedExportsOrder,default:()=>WritingTable_stories});var icons=__webpack_require__("./src/assets/icons/index.ts"),style=__webpack_require__("./src/constants/style.ts"),usePageNavigate=__webpack_require__("./src/hooks/usePageNavigate.ts"),react=__webpack_require__("./node_modules/react/index.js"),styled_components_browser_esm=__webpack_require__("./node_modules/styled-components/dist/styled-components.browser.esm.js"),date=__webpack_require__("./src/utils/date.ts"),jsx_runtime=__webpack_require__("./node_modules/react/jsx-runtime.js");const blogIcon={MEDIUM:(0,jsx_runtime.jsx)(icons.Xx,{width:"2.4rem",height:"2.4rem"}),TISTORY:(0,jsx_runtime.jsx)(icons.hr,{width:"2.4rem",height:"2.4rem"})},WritingTable=({writings,categoryId,isMobile=!1})=>{const{goWritingPage}=(0,usePageNavigate.L)(),rowRef=(0,react.useRef)(null);return(0,react.useEffect)((()=>{rowRef.current?.focus()}),[writings]),(0,jsx_runtime.jsxs)(S.WritingTableContainer,{summary:"카테고리 내부 글 목록을 나타낸",children:[(0,jsx_runtime.jsxs)("colgroup",{children:[(0,jsx_runtime.jsx)("col",{width:"60%"}),(0,jsx_runtime.jsx)("col",{width:"20%"}),isMobile?null:(0,jsx_runtime.jsx)("col",{width:"20%"})]}),(0,jsx_runtime.jsx)("thead",{children:(0,jsx_runtime.jsxs)("tr",{ref:rowRef,tabIndex:0,children:[(0,jsx_runtime.jsx)("th",{children:"글 제목"}),isMobile?null:(0,jsx_runtime.jsx)("th",{children:"생성 날짜"}),isMobile?(0,jsx_runtime.jsx)("th",{children:"블로그"}):(0,jsx_runtime.jsx)("th",{children:"발행한 블로그 플랫폼"})]})}),(0,jsx_runtime.jsx)("tbody",{children:writings.map((({id,title,publishedDetails,createdAt})=>(0,jsx_runtime.jsxs)("tr",{onClick:()=>goWritingPage({categoryId,writingId:id,isDeletedWriting:!1}),role:"button",tabIndex:0,children:[(0,jsx_runtime.jsx)("td",{children:title}),isMobile?null:(0,jsx_runtime.jsx)("td",{children:(0,date.v)(createdAt,"YYYY.MM.DD.")}),(0,jsx_runtime.jsx)("td",{children:(0,jsx_runtime.jsx)(S.PublishedToIconContainer,{children:publishedDetails.map((({blogName},index)=>(0,jsx_runtime.jsx)(react.Fragment,{children:blogIcon[blogName]},index)))})})]},id)))})]})};WritingTable.displayName="WritingTable";const WritingTable_WritingTable=WritingTable,S={WritingTableContainer:styled_components_browser_esm.zo.table`
    width: 100%;
    padding-bottom: 8rem;

    @media (max-width: ${style.d.mobileLarge}) {
      padding-bottom: 4rem;
    }

    text-align: left;
    font-size: 1.4rem;
    table-layout: fixed;

    th {
      color: ${({theme})=>theme.color.gray8};
    }

    tr {
      height: 4.2rem;
    }

    td {
      overflow: hidden;
      text-overflow: ellipsis;
      white-space: nowrap;

      .publishedTo {
        display: flex;
        gap: 0.8rem;
      }
    }

    th,
    td {
      padding: 0 1rem;
      border: 1px solid ${({theme})=>theme.color.gray5};
    }

    th:first-child,
    td:first-child {
      border-left: none;
    }

    th:last-child,
    td:last-child {
      border-right: none;
    }

    tbody tr:hover {
      cursor: pointer;
      background-color: ${({theme})=>theme.color.gray3};
    }
  `,PublishedToIconContainer:styled_components_browser_esm.zo.div`
    display: flex;
    gap: 0.8rem;
  `};try{WritingTable.displayName="WritingTable",WritingTable.__docgenInfo={description:"",displayName:"WritingTable",props:{writings:{defaultValue:null,description:"",name:"writings",required:!0,type:{name:"Writing[]"}},categoryId:{defaultValue:null,description:"",name:"categoryId",required:!0,type:{name:"number"}},isMobile:{defaultValue:{value:"false"},description:"",name:"isMobile",required:!1,type:{name:"boolean"}}}},"undefined"!=typeof STORYBOOK_REACT_CLASSES&&(STORYBOOK_REACT_CLASSES["src/components/WritingTable/WritingTable.tsx#WritingTable"]={docgenInfo:WritingTable.__docgenInfo,name:"WritingTable",path:"src/components/WritingTable/WritingTable.tsx#WritingTable"})}catch(__react_docgen_typescript_loader_error){}var storybook=__webpack_require__("./src/styles/storybook.ts"),writingTablePage=__webpack_require__("./src/mocks/data/writingTablePage.ts");const WritingTable_stories={title:"writing/WritingTable",component:WritingTable_WritingTable,args:{writings:writingTablePage.H.writings,categoryId:1}},Playground={render:({writings,categoryId})=>(0,jsx_runtime.jsx)(storybook.y1,{children:(0,jsx_runtime.jsxs)(storybook.CA,{style:{width:"800px"},children:[(0,jsx_runtime.jsx)(storybook.VP,{children:"기본"}),(0,jsx_runtime.jsx)(WritingTable_WritingTable,{categoryId,writings})]})})};Playground.parameters={...Playground.parameters,docs:{...Playground.parameters?.docs,source:{originalSource:"{\n  render: ({\n    writings,\n    categoryId\n  }) => {\n    return <StoryContainer>\n        <StoryItemContainer style={{\n        width: '800px'\n      }}>\n          <StoryItemTitle>기본</StoryItemTitle>\n          <WritingTable categoryId={categoryId} writings={writings} />\n        </StoryItemContainer>\n      </StoryContainer>;\n  }\n}",...Playground.parameters?.docs?.source}}};const __namedExportsOrder=["Playground"]},"./src/styles/storybook.ts":(__unused_webpack_module,__webpack_exports__,__webpack_require__)=>{__webpack_require__.d(__webpack_exports__,{CA:()=>StoryItemContainer,VP:()=>StoryItemTitle,y1:()=>StoryContainer});var styled_components__WEBPACK_IMPORTED_MODULE_0__=__webpack_require__("./node_modules/styled-components/dist/styled-components.browser.esm.js");const StoryContainer=styled_components__WEBPACK_IMPORTED_MODULE_0__.zo.div`
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
//# sourceMappingURL=components-WritingTable-WritingTable-stories.157fe46c.iframe.bundle.js.map