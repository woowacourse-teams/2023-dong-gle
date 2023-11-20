"use strict";(self.webpackChunkfrontend=self.webpackChunkfrontend||[]).push([[50],{"./src/components/@common/Menu/Menu.stories.tsx":(__unused_webpack_module,__webpack_exports__,__webpack_require__)=>{__webpack_require__.r(__webpack_exports__),__webpack_require__.d(__webpack_exports__,{Playground:()=>Playground,__namedExportsOrder:()=>__namedExportsOrder,default:()=>__WEBPACK_DEFAULT_EXPORT__});var _Menu__WEBPACK_IMPORTED_MODULE_0__=__webpack_require__("./src/components/@common/Menu/Menu.tsx"),styles_storybook__WEBPACK_IMPORTED_MODULE_1__=__webpack_require__("./src/styles/storybook.ts"),react_jsx_runtime__WEBPACK_IMPORTED_MODULE_2__=__webpack_require__("./node_modules/react/jsx-runtime.js");const __WEBPACK_DEFAULT_EXPORT__={title:"common/Menu",args:{initialIsOpen:!0,verticalDirection:"down",horizonDirection:"left"},argTypes:{initialIsOpen:{description:"메뉴의 초기 열림/닫힘 상태입니다."},verticalDirection:{description:"메뉴가 수직을 기준으로 렌더링 되는 위치입니다."},horizonDirection:{description:"메뉴가 수평을 기준으로 렌더링 되는 위치입니다."}},component:_Menu__WEBPACK_IMPORTED_MODULE_0__.Z},MenuValues=[{title:"도움말",handleMenuItemClick:()=>{}},{title:"도움말도움말도움말도움말도움말도움말도움말도움말",handleMenuItemClick:()=>{}},{title:"도움말",handleMenuItemClick:()=>{}},{title:"도움말",handleMenuItemClick:()=>{}}],Playground={render:args=>(0,react_jsx_runtime__WEBPACK_IMPORTED_MODULE_2__.jsx)(styles_storybook__WEBPACK_IMPORTED_MODULE_1__.y1,{children:(0,react_jsx_runtime__WEBPACK_IMPORTED_MODULE_2__.jsx)(styles_storybook__WEBPACK_IMPORTED_MODULE_1__.CA,{children:(0,react_jsx_runtime__WEBPACK_IMPORTED_MODULE_2__.jsx)(_Menu__WEBPACK_IMPORTED_MODULE_0__.Z,{verticalDirection:args.verticalDirection,horizonDirection:args.horizonDirection,children:MenuValues.map((({title,handleMenuItemClick})=>(0,react_jsx_runtime__WEBPACK_IMPORTED_MODULE_2__.jsx)(_Menu__WEBPACK_IMPORTED_MODULE_0__.Z.Item,{title,handleMenuItemClick},title)))})})})};Playground.parameters={...Playground.parameters,docs:{...Playground.parameters?.docs,source:{originalSource:"{\n  render: args => {\n    return <StoryContainer>\n        <StoryItemContainer>\n          <Menu verticalDirection={args.verticalDirection} horizonDirection={args.horizonDirection}>\n            {MenuValues.map(({\n            title,\n            handleMenuItemClick\n          }) => {\n            return <Menu.Item key={title} title={title} handleMenuItemClick={handleMenuItemClick} />;\n          })}\n          </Menu>\n        </StoryItemContainer>\n      </StoryContainer>;\n  }\n}",...Playground.parameters?.docs?.source}}};const __namedExportsOrder=["Playground"]},"./src/components/@common/Menu/Menu.tsx":(__unused_webpack_module,__webpack_exports__,__webpack_require__)=>{__webpack_require__.d(__webpack_exports__,{Z:()=>Menu_Menu});var styled_components_browser_esm=__webpack_require__("./node_modules/styled-components/dist/styled-components.browser.esm.js"),jsx_runtime=__webpack_require__("./node_modules/react/jsx-runtime.js");const Item=({title,handleMenuItemClick,icon})=>(0,jsx_runtime.jsx)(S.Item,{children:(0,jsx_runtime.jsxs)("button",{onClick:handleMenuItemClick,children:[title,icon]})});Item.displayName="Item";const Menu_Item=Item,S={Item:styled_components_browser_esm.ZP.li`
    display: flex;
    align-items: center;
    width: 100%;
    height: 4rem;
    padding: 0 0.4rem;
    &:not(:last-child) {
      box-shadow: 0px 1px 0px ${({theme})=>theme.color.gray4};
    }

    &:hover {
      background-color: ${({theme})=>theme.color.gray4};
    }

    button {
      display: flex;
      justify-content: space-between;
      align-items: center;
      width: 100px;
      height: 100%;
      margin: 0 8px;

      overflow: hidden;
      white-space: nowrap;
      text-overflow: ellipsis;
    }
  `};try{Item.displayName="Item",Item.__docgenInfo={description:"",displayName:"Item",props:{title:{defaultValue:null,description:"",name:"title",required:!0,type:{name:"string"}},handleMenuItemClick:{defaultValue:null,description:"",name:"handleMenuItemClick",required:!0,type:{name:"() => void"}},icon:{defaultValue:null,description:"",name:"icon",required:!1,type:{name:"ReactElement<any, string | JSXElementConstructor<any>>"}}}},"undefined"!=typeof STORYBOOK_REACT_CLASSES&&(STORYBOOK_REACT_CLASSES["src/components/@common/Menu/Item.tsx#Item"]={docgenInfo:Item.__docgenInfo,name:"Item",path:"src/components/@common/Menu/Item.tsx#Item"})}catch(__react_docgen_typescript_loader_error){}var react=__webpack_require__("./node_modules/react/index.js");const _common_useOutsideClickEffect=(ref,onClickOutside)=>{(0,react.useEffect)((()=>{const handleClickOutside=e=>{ref.current&&!ref.current.contains(e.target)&&onClickOutside(e)};return document.addEventListener("mousedown",handleClickOutside),()=>{document.removeEventListener("mousedown",handleClickOutside)}}),[ref,onClickOutside])},Menu=({initialIsOpen=!1,verticalDirection="down",horizonDirection="left",children})=>{const{menuRef,isOpen,toggleIsOpen}=(initialIsOpen=>{const[isOpen,setIsOpen]=(0,react.useState)(initialIsOpen),menuRef=(0,react.useRef)(null);return _common_useOutsideClickEffect(menuRef,(()=>setIsOpen(!1))),{menuRef,isOpen,toggleIsOpen:()=>setIsOpen(!isOpen)}})(initialIsOpen);return(0,jsx_runtime.jsx)(Menu_S.Menu,{ref:menuRef,onClick:toggleIsOpen,children:isOpen?(0,jsx_runtime.jsx)(Menu_S.MenuList,{$verticalDirection:verticalDirection,$horizonDirection:horizonDirection,children}):null})};Menu.displayName="Menu",Menu.Item=Menu_Item;const Menu_Menu=Menu,Menu_S={Menu:styled_components_browser_esm.ZP.div`
    position: absolute;
    top: 0;
    left: 0;
    width: 100%;
    height: 100%;
  `,MenuList:styled_components_browser_esm.ZP.ul`
    display: flex;
    flex-direction: column;
    position: absolute;
    ${({$verticalDirection})=>"up"===$verticalDirection&&"\n    \tbottom: 100%;\n  \t"}
    ${({$verticalDirection})=>"down"===$verticalDirection&&"\n\t\t\ttop: 100%;\n  \t"}
    ${({$horizonDirection})=>"right"===$horizonDirection&&"\n    \tleft: 0;\n  \t"}
		${({$horizonDirection})=>"left"===$horizonDirection&&"\n    \tright: 0;\n  \t"}
    width: fit-content;

    border: 1px solid ${({theme})=>theme.color.gray4};
    border-radius: 4px;
    background-color: ${({theme})=>theme.color.gray1};
    box-shadow: 0px 0px 8px rgba(0, 0, 0, 0.1);

    z-index: 1;
  `};try{Menu.displayName="Menu",Menu.__docgenInfo={description:"",displayName:"Menu",props:{initialIsOpen:{defaultValue:{value:"false"},description:"",name:"initialIsOpen",required:!1,type:{name:"boolean"}},verticalDirection:{defaultValue:{value:"down"},description:"",name:"verticalDirection",required:!1,type:{name:"enum",value:[{value:'"up"'},{value:'"down"'}]}},horizonDirection:{defaultValue:{value:"left"},description:"",name:"horizonDirection",required:!1,type:{name:"enum",value:[{value:'"left"'},{value:'"right"'}]}}}},"undefined"!=typeof STORYBOOK_REACT_CLASSES&&(STORYBOOK_REACT_CLASSES["src/components/@common/Menu/Menu.tsx#Menu"]={docgenInfo:Menu.__docgenInfo,name:"Menu",path:"src/components/@common/Menu/Menu.tsx#Menu"})}catch(__react_docgen_typescript_loader_error){}},"./src/styles/storybook.ts":(__unused_webpack_module,__webpack_exports__,__webpack_require__)=>{__webpack_require__.d(__webpack_exports__,{CA:()=>StoryItemContainer,VP:()=>StoryItemTitle,y1:()=>StoryContainer});var styled_components__WEBPACK_IMPORTED_MODULE_0__=__webpack_require__("./node_modules/styled-components/dist/styled-components.browser.esm.js");const StoryContainer=styled_components__WEBPACK_IMPORTED_MODULE_0__.zo.div`
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
//# sourceMappingURL=components-common-Menu-Menu-stories.d9710033.iframe.bundle.js.map