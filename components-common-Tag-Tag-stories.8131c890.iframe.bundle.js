"use strict";(self.webpackChunkfrontend=self.webpackChunkfrontend||[]).push([[579],{"./src/components/@common/Tag/Tag.stories.tsx":(__unused_webpack_module,__webpack_exports__,__webpack_require__)=>{__webpack_require__.r(__webpack_exports__),__webpack_require__.d(__webpack_exports__,{Playground:()=>Playground,Removable:()=>Removable,__namedExportsOrder:()=>__namedExportsOrder,default:()=>__WEBPACK_DEFAULT_EXPORT__});var styles_storybook__WEBPACK_IMPORTED_MODULE_0__=__webpack_require__("./src/styles/storybook.ts"),_Tag__WEBPACK_IMPORTED_MODULE_1__=__webpack_require__("./src/components/@common/Tag/Tag.tsx"),react_jsx_runtime__WEBPACK_IMPORTED_MODULE_2__=__webpack_require__("./node_modules/react/jsx-runtime.js");const __WEBPACK_DEFAULT_EXPORT__={title:"common/Tag",component:_Tag__WEBPACK_IMPORTED_MODULE_1__.Z,args:{removable:!0,children:"태그"},argTypes:{removable:{description:"삭제 가능한 태그인지 여부입니다.",control:{type:"boolean"}},children:{description:"태그 내부 content에 해당합니다.",control:{type:"text"}}}},Playground={},Removable={render:()=>(0,react_jsx_runtime__WEBPACK_IMPORTED_MODULE_2__.jsxs)(styles_storybook__WEBPACK_IMPORTED_MODULE_0__.y1,{children:[(0,react_jsx_runtime__WEBPACK_IMPORTED_MODULE_2__.jsxs)(styles_storybook__WEBPACK_IMPORTED_MODULE_0__.CA,{children:[(0,react_jsx_runtime__WEBPACK_IMPORTED_MODULE_2__.jsx)(styles_storybook__WEBPACK_IMPORTED_MODULE_0__.VP,{children:"true"}),(0,react_jsx_runtime__WEBPACK_IMPORTED_MODULE_2__.jsx)(_Tag__WEBPACK_IMPORTED_MODULE_1__.Z,{removable:!0,children:"삭제가능"})]}),(0,react_jsx_runtime__WEBPACK_IMPORTED_MODULE_2__.jsxs)(styles_storybook__WEBPACK_IMPORTED_MODULE_0__.CA,{children:[(0,react_jsx_runtime__WEBPACK_IMPORTED_MODULE_2__.jsx)(styles_storybook__WEBPACK_IMPORTED_MODULE_0__.VP,{children:"false"}),(0,react_jsx_runtime__WEBPACK_IMPORTED_MODULE_2__.jsx)(_Tag__WEBPACK_IMPORTED_MODULE_1__.Z,{removable:!1,children:"삭제불가"})]})]})};Playground.parameters={...Playground.parameters,docs:{...Playground.parameters?.docs,source:{originalSource:"{}",...Playground.parameters?.docs?.source}}},Removable.parameters={...Removable.parameters,docs:{...Removable.parameters?.docs,source:{originalSource:"{\n  render: () => {\n    return <StoryContainer>\n        <StoryItemContainer>\n          <StoryItemTitle>true</StoryItemTitle>\n          <Tag removable>삭제가능</Tag>\n        </StoryItemContainer>\n        <StoryItemContainer>\n          <StoryItemTitle>false</StoryItemTitle>\n          <Tag removable={false}>삭제불가</Tag>\n        </StoryItemContainer>\n      </StoryContainer>;\n  }\n}",...Removable.parameters?.docs?.source}}};const __namedExportsOrder=["Playground","Removable"]},"./src/components/@common/Tag/Tag.tsx":(__unused_webpack_module,__webpack_exports__,__webpack_require__)=>{__webpack_require__.d(__webpack_exports__,{Z:()=>__WEBPACK_DEFAULT_EXPORT__});var styled_components__WEBPACK_IMPORTED_MODULE_2__=__webpack_require__("./node_modules/styled-components/dist/styled-components.browser.esm.js"),assets_icons__WEBPACK_IMPORTED_MODULE_0__=__webpack_require__("./src/assets/icons/index.ts"),react_jsx_runtime__WEBPACK_IMPORTED_MODULE_1__=__webpack_require__("./node_modules/react/jsx-runtime.js");const Tag=({removable=!0,children,...rest})=>(0,react_jsx_runtime__WEBPACK_IMPORTED_MODULE_1__.jsxs)(S.Tag,{...rest,children:["#",children,removable&&(0,react_jsx_runtime__WEBPACK_IMPORTED_MODULE_1__.jsx)(assets_icons__WEBPACK_IMPORTED_MODULE_0__.Tw,{width:14,height:14})]});Tag.displayName="Tag";const __WEBPACK_DEFAULT_EXPORT__=Tag,S={Tag:styled_components__WEBPACK_IMPORTED_MODULE_2__.zo.button`
    display: inline-flex;
    align-items: center;
    padding: 0.6rem;
    background-color: ${({theme})=>theme.color.gray4};
    border-radius: 8px;
    color: ${({theme})=>theme.color.gray8};
    font-size: 1.2rem;
    font-weight: 600;
  `};try{Tag.displayName="Tag",Tag.__docgenInfo={description:"",displayName:"Tag",props:{removable:{defaultValue:{value:"true"},description:"",name:"removable",required:!1,type:{name:"boolean"}}}},"undefined"!=typeof STORYBOOK_REACT_CLASSES&&(STORYBOOK_REACT_CLASSES["src/components/@common/Tag/Tag.tsx#Tag"]={docgenInfo:Tag.__docgenInfo,name:"Tag",path:"src/components/@common/Tag/Tag.tsx#Tag"})}catch(__react_docgen_typescript_loader_error){}},"./src/styles/storybook.ts":(__unused_webpack_module,__webpack_exports__,__webpack_require__)=>{__webpack_require__.d(__webpack_exports__,{CA:()=>StoryItemContainer,VP:()=>StoryItemTitle,y1:()=>StoryContainer});var styled_components__WEBPACK_IMPORTED_MODULE_0__=__webpack_require__("./node_modules/styled-components/dist/styled-components.browser.esm.js");const StoryContainer=styled_components__WEBPACK_IMPORTED_MODULE_0__.zo.div`
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
`)}}]);
//# sourceMappingURL=components-common-Tag-Tag-stories.8131c890.iframe.bundle.js.map