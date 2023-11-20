"use strict";(self.webpackChunkfrontend=self.webpackChunkfrontend||[]).push([[130],{"./src/components/@common/Modal/Modal.stories.tsx":(__unused_webpack_module,__webpack_exports__,__webpack_require__)=>{__webpack_require__.r(__webpack_exports__),__webpack_require__.d(__webpack_exports__,{Playground:()=>Playground,__namedExportsOrder:()=>__namedExportsOrder,default:()=>Modal_stories});var react=__webpack_require__("./node_modules/react/index.js"),react_dom=__webpack_require__("./node_modules/react-dom/index.js"),styled_components_browser_esm=__webpack_require__("./node_modules/styled-components/dist/styled-components.browser.esm.js"),icons=__webpack_require__("./src/assets/icons/index.ts"),jsx_runtime=__webpack_require__("./node_modules/react/jsx-runtime.js");const Modal=({isOpen=!0,canBackdropClose=!0,canEscKeyClose=!0,hasCloseButton=!0,closeModal,children,...rest})=>{const myRef=(0,react.useRef)(null),onKeyDownEscape=(0,react.useCallback)((event=>{"Escape"===event.key&&closeModal()}),[closeModal]);return(0,react.useEffect)((()=>(isOpen&&(document.body.style.overflow="hidden",myRef.current?.focus()),()=>{document.body.style.overflow="auto"})),[isOpen]),(0,react.useEffect)((()=>(isOpen&&canEscKeyClose&&window.addEventListener("keydown",onKeyDownEscape),()=>{window.removeEventListener("keydown",onKeyDownEscape)})),[isOpen,canEscKeyClose,onKeyDownEscape]),(0,react_dom.createPortal)((0,jsx_runtime.jsx)(S.ModalWrapper,{children:isOpen&&(0,jsx_runtime.jsxs)(jsx_runtime.Fragment,{children:[(0,jsx_runtime.jsx)(S.Backdrop,{onClick:canBackdropClose?closeModal:void 0}),(0,jsx_runtime.jsxs)(S.Content,{ref:myRef,"aria-modal":isOpen,...rest,children:[hasCloseButton&&(0,jsx_runtime.jsx)(S.CloseButton,{type:"button",onClick:closeModal,"aria-label":"모달 닫기",children:(0,jsx_runtime.jsx)(icons.Tw,{width:24,height:24})}),children]})]})}),document.body)},Modal_Modal=Modal,S={ModalWrapper:styled_components_browser_esm.zo.div`
    position: relative;
    z-index: 9;
  `,Backdrop:styled_components_browser_esm.zo.div`
    position: fixed;
    inset: 0;
    background: ${({theme})=>theme.color.modalBackdrop};
  `,Content:styled_components_browser_esm.zo.dialog`
    position: fixed;
    inset: 50% auto auto 50%;
    display: flex;
    justify-content: center;
    min-width: 20vw;
    max-height: 80vh;
    overflow: auto;
    padding: 2.5rem;
    border: none;
    border-radius: 8px;
    background-color: ${({theme})=>theme.color.gray1};
    transform: translate(-50%, -50%);
  `,CloseButton:styled_components_browser_esm.zo.button`
    position: absolute;
    inset: 2.5rem 2.5rem auto auto;
    border-radius: 4px;

    &:hover {
      background-color: ${({theme})=>theme.color.gray4};
    }
  `};try{Modal.displayName="Modal",Modal.__docgenInfo={description:"",displayName:"Modal",props:{isOpen:{defaultValue:{value:"true"},description:"",name:"isOpen",required:!1,type:{name:"boolean"}},canBackdropClose:{defaultValue:{value:"true"},description:"",name:"canBackdropClose",required:!1,type:{name:"boolean"}},canEscKeyClose:{defaultValue:{value:"true"},description:"",name:"canEscKeyClose",required:!1,type:{name:"boolean"}},hasCloseButton:{defaultValue:{value:"true"},description:"",name:"hasCloseButton",required:!1,type:{name:"boolean"}},closeModal:{defaultValue:null,description:"",name:"closeModal",required:!0,type:{name:"() => void"}}}},"undefined"!=typeof STORYBOOK_REACT_CLASSES&&(STORYBOOK_REACT_CLASSES["src/components/@common/Modal/Modal.tsx#Modal"]={docgenInfo:Modal.__docgenInfo,name:"Modal",path:"src/components/@common/Modal/Modal.tsx#Modal"})}catch(__react_docgen_typescript_loader_error){}var Button=__webpack_require__("./src/components/@common/Button/Button.tsx");const Modal_stories={title:"common/Modal",component:Modal_Modal,argTypes:{children:{control:!1},isOpen:{control:!1}},args:{isOpen:!1}},Playground={render:({...rest})=>{const{isOpen,openModal,closeModal}=(()=>{const[isOpen,setIsOpen]=(0,react.useState)(!1);return{isOpen,openModal:()=>{setIsOpen(!0)},closeModal:()=>{setIsOpen(!1)}}})();return(0,jsx_runtime.jsxs)(jsx_runtime.Fragment,{children:[(0,jsx_runtime.jsx)(Button.ZP,{variant:"secondary",onClick:openModal,children:"Open Modal"}),(0,jsx_runtime.jsx)(Modal_Modal,{...rest,isOpen,closeModal,children:(0,jsx_runtime.jsxs)(ModalContent,{children:[(0,jsx_runtime.jsx)("h1",{children:"모달"}),(0,jsx_runtime.jsx)("p",{children:"내용을 마음껏 써주세요."})]})})]})}},ModalContent=styled_components_browser_esm.zo.div`
  display: flex;
  flex-direction: column;
  align-items: center;

  height: 30rem;
  p {
    margin-top: 13rem;
    font-size: 1.3rem;
  }
`;Playground.parameters={...Playground.parameters,docs:{...Playground.parameters?.docs,source:{originalSource:"{\n  render: ({\n    ...rest\n  }) => {\n    const {\n      isOpen,\n      openModal,\n      closeModal\n    } = useModal();\n    return <>\n        <Button variant='secondary' onClick={openModal}>\n          Open Modal\n        </Button>\n        <Modal {...rest} isOpen={isOpen} closeModal={closeModal}>\n          <ModalContent>\n            <h1>모달</h1>\n            <p>내용을 마음껏 써주세요.</p>\n          </ModalContent>\n        </Modal>\n      </>;\n  }\n}",...Playground.parameters?.docs?.source}}};const __namedExportsOrder=["Playground"]}}]);
//# sourceMappingURL=components-common-Modal-Modal-stories.81cd748f.iframe.bundle.js.map