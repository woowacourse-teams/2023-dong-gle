"use strict";(self.webpackChunkfrontend=self.webpackChunkfrontend||[]).push([[724],{"./src/components/@common/Input/Input.stories.tsx":(__unused_webpack_module,__webpack_exports__,__webpack_require__)=>{__webpack_require__.r(__webpack_exports__),__webpack_require__.d(__webpack_exports__,{Playground:()=>Playground,__namedExportsOrder:()=>__namedExportsOrder,default:()=>__WEBPACK_DEFAULT_EXPORT__});var _Input__WEBPACK_IMPORTED_MODULE_0__=__webpack_require__("./src/components/@common/Input/Input.tsx"),constants_components_common__WEBPACK_IMPORTED_MODULE_1__=__webpack_require__("./src/constants/components/common.ts");const __WEBPACK_DEFAULT_EXPORT__={title:"common/Input",component:_Input__WEBPACK_IMPORTED_MODULE_0__.Z,args:{variant:"outline",size:"medium",labelText:"라벨",supportingText:"안내 문구는 여기에 나타납니다",isError:!1,required:!0,placeholder:"placeholder"},argTypes:{variant:{description:"미리 정의해놓은 인풋의 스타일입니다.",options:Object.values(_Input__WEBPACK_IMPORTED_MODULE_0__.J),control:{type:"radio"}},size:{description:"크기에 따라 padding과 font-size가 바뀝니다.",options:Object.values(constants_components_common__WEBPACK_IMPORTED_MODULE_1__.$),control:{type:"radio"}},labelText:{description:"라벨 텍스트입니다.",control:{type:"text"}},supportingText:{description:"인풋 아래에 나타나는 안내 문구 텍스트입니다.",control:{type:"text"}},placeholder:{description:"인풋 안에 나타나는 placeholder 텍스트입니다.",control:{type:"text"}},isError:{description:"Error 상태를 나타냅니다.",control:{type:"boolean"}},required:{description:"input의 필수 입력 여부를 나타냅니다.",control:{type:"boolean"}}}},Playground={};Playground.parameters={...Playground.parameters,docs:{...Playground.parameters?.docs,source:{originalSource:"{}",...Playground.parameters?.docs?.source}}};const __namedExportsOrder=["Playground"]},"./src/components/@common/Input/Input.tsx":(__unused_webpack_module,__webpack_exports__,__webpack_require__)=>{__webpack_require__.d(__webpack_exports__,{J:()=>InputVariant,Z:()=>__WEBPACK_DEFAULT_EXPORT__});var react__WEBPACK_IMPORTED_MODULE_0__=__webpack_require__("./node_modules/react/index.js"),styled_components__WEBPACK_IMPORTED_MODULE_2__=__webpack_require__("./node_modules/styled-components/dist/styled-components.browser.esm.js"),react_jsx_runtime__WEBPACK_IMPORTED_MODULE_1__=__webpack_require__("./node_modules/react/jsx-runtime.js");const InputVariant=["outline","filled","unstyled","underlined"],Input=({size="medium",labelText,supportingText,variant="outline",isError=!1,...rest},ref)=>{const inputId=(0,react__WEBPACK_IMPORTED_MODULE_0__.useId)();return(0,react_jsx_runtime__WEBPACK_IMPORTED_MODULE_1__.jsxs)(S.InputContainer,{children:[labelText&&(0,react_jsx_runtime__WEBPACK_IMPORTED_MODULE_1__.jsx)(S.Label,{htmlFor:inputId,$required:rest.required,$variant:variant,children:labelText}),(0,react_jsx_runtime__WEBPACK_IMPORTED_MODULE_1__.jsx)(S.Input,{id:inputId,ref,$size:size,$variant:variant,$isError:isError,...rest}),supportingText&&(0,react_jsx_runtime__WEBPACK_IMPORTED_MODULE_1__.jsx)(S.SupportingText,{$isError:isError,children:supportingText})]})};Input.displayName="Input";const __WEBPACK_DEFAULT_EXPORT__=(0,react__WEBPACK_IMPORTED_MODULE_0__.forwardRef)(Input),S={InputContainer:styled_components__WEBPACK_IMPORTED_MODULE_2__.zo.div`
    display: flex;
    flex-direction: column;
    gap: 0.6rem;
    font-size: 1.3rem;
  `,Label:styled_components__WEBPACK_IMPORTED_MODULE_2__.zo.label`
    font-weight: 500;
    ${({$required,theme})=>$required&&styled_components__WEBPACK_IMPORTED_MODULE_2__.iv`
        &::after {
          content: '*';
          margin-left: 0.2rem;
          color: ${theme.color.red6};
        }
      `};
  `,Input:styled_components__WEBPACK_IMPORTED_MODULE_2__.zo.input`
    border: none;
    border-radius: 4px;
    background-color: transparent;

    ${({$size})=>{return size=$size,{small:styled_components__WEBPACK_IMPORTED_MODULE_2__.iv`
      padding: 0.6rem 0.6rem;
      font-size: 1.3rem;
    `,medium:styled_components__WEBPACK_IMPORTED_MODULE_2__.iv`
      padding: 0.8rem 1rem;
      font-size: 1.4rem;
    `,large:styled_components__WEBPACK_IMPORTED_MODULE_2__.iv`
      padding: 1rem 1.2rem;
      font-size: 1.5rem;
    `}[size];var size}};
    ${({$variant,$isError})=>{return variant=$variant,isError=$isError,{outline:styled_components__WEBPACK_IMPORTED_MODULE_2__.iv`
      ${({theme})=>styled_components__WEBPACK_IMPORTED_MODULE_2__.iv`
        border: 1px solid ${isError?theme.color.red6:theme.color.gray6};
        outline: 1px solid transparent;

        &:focus {
          border: 1px solid ${isError?theme.color.red6:theme.color.gray6};
          outline: 1px solid ${isError?theme.color.red6:theme.color.gray8};
        }
      `}
    `,filled:styled_components__WEBPACK_IMPORTED_MODULE_2__.iv`
      ${({theme})=>styled_components__WEBPACK_IMPORTED_MODULE_2__.iv`
        background-color: ${isError?theme.color.red1:theme.color.gray4};
        border: 1px solid transparent;
        outline: 1px solid transparent;

        &:focus {
          background-color: transparent;
          outline: 1px solid ${isError?theme.color.red6:theme.color.gray8};
        }
      `}
    `,unstyled:styled_components__WEBPACK_IMPORTED_MODULE_2__.iv`
      ${({theme})=>styled_components__WEBPACK_IMPORTED_MODULE_2__.iv`
        border: 1px solid transparent;
        outline: 1px solid ${isError?theme.color.red6:theme.color.gray1};
      `}
    `,underlined:styled_components__WEBPACK_IMPORTED_MODULE_2__.iv`
      ${({theme})=>styled_components__WEBPACK_IMPORTED_MODULE_2__.iv`
        border: 1px solid transparent;
        border-bottom: 1px solid ${isError?theme.color.red6:theme.color.gray6};
        border-radius: 0;
        outline: 1px solid transparent;
      `}
    `}[variant];var variant,isError}};

    &::placeholder {
      color: ${({theme})=>theme.color.gray6};
    }
  `,SupportingText:styled_components__WEBPACK_IMPORTED_MODULE_2__.zo.p`
    color: ${({$isError,theme})=>$isError?theme.color.red6:theme.color.gray7};
  `,Underline:styled_components__WEBPACK_IMPORTED_MODULE_2__.zo.div`
    position: absolute;
    bottom: 0;
    left: 0;
    height: 2px;
    width: 100%;
    background-color: ${({theme})=>theme.color.primary};
    transform: scaleX(0);
    transition: all 0.3s ease;
  `}},"./src/constants/components/common.ts":(__unused_webpack_module,__webpack_exports__,__webpack_require__)=>{__webpack_require__.d(__webpack_exports__,{$:()=>Size});const Size=["small","medium","large"]}}]);
//# sourceMappingURL=components-common-Input-Input-stories.acd415ef.iframe.bundle.js.map