$.validator.addMethod("phoneRegex", function(value, element) {
    return this.optional(element) || /^(0?)(3[2-9]|5[6|8|9]|7[0|6-9]|8[0-6|8|9]|9[0-4|6-9])[0-9]{7}$/.test(value);
}, "Số điện thoại không hợp lệ");
$.validator.addMethod("passwordRegex", function(value, element) {
    return this.optional(element) || /^(?=.*\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[a-zA-Z\d]).{8,}$/gm.test(value);
}, "Mật khẩu không hợp lệ");
/******/
(function (modules) { // webpackBootstrap
    /******/ 	// The module cache
    /******/
    var installedModules = {};
    /******/
    /******/ 	// The require function
    /******/
    function __webpack_require__(moduleId) {
        /******/
        /******/ 		// Check if module is in cache
        /******/
        if (installedModules[moduleId]) {
            /******/
            return installedModules[moduleId].exports;
            /******/
        }
        /******/ 		// Create a new module (and put it into the cache)
        /******/
        var module = installedModules[moduleId] = {
            /******/            i: moduleId,
            /******/            l: false,
            /******/            exports: {}
            /******/
        };
        /******/
        /******/ 		// Execute the module function
        /******/
        modules[moduleId].call(module.exports, module, module.exports, __webpack_require__);
        /******/
        /******/ 		// Flag the module as loaded
        /******/
        module.l = true;
        /******/
        /******/ 		// Return the exports of the module
        /******/
        return module.exports;
        /******/
    }

    /******/
    /******/
    /******/ 	// expose the modules object (__webpack_modules__)
    /******/
    __webpack_require__.m = modules;
    /******/
    /******/ 	// expose the module cache
    /******/
    __webpack_require__.c = installedModules;
    /******/
    /******/ 	// define getter function for harmony exports
    /******/
    __webpack_require__.d = function (exports, name, getter) {
        /******/
        if (!__webpack_require__.o(exports, name)) {
            /******/
            Object.defineProperty(exports, name, {enumerable: true, get: getter});
            /******/
        }
        /******/
    };
    /******/
    /******/ 	// define __esModule on exports
    /******/
    __webpack_require__.r = function (exports) {
        /******/
        if (typeof Symbol !== 'undefined' && Symbol.toStringTag) {
            /******/
            Object.defineProperty(exports, Symbol.toStringTag, {value: 'Module'});
            /******/
        }
        /******/
        Object.defineProperty(exports, '__esModule', {value: true});
        /******/
    };
    /******/
    /******/ 	// create a fake namespace object
    /******/ 	// mode & 1: value is a module id, require it
    /******/ 	// mode & 2: merge all properties of value into the ns
    /******/ 	// mode & 4: return value when already ns object
    /******/ 	// mode & 8|1: behave like require
    /******/
    __webpack_require__.t = function (value, mode) {
        /******/
        if (mode & 1) value = __webpack_require__(value);
        /******/
        if (mode & 8) return value;
        /******/
        if ((mode & 4) && typeof value === 'object' && value && value.__esModule) return value;
        /******/
        var ns = Object.create(null);
        /******/
        __webpack_require__.r(ns);
        /******/
        Object.defineProperty(ns, 'default', {enumerable: true, value: value});
        /******/
        if (mode & 2 && typeof value != 'string') for (var key in value) __webpack_require__.d(ns, key, function (key) {
            return value[key];
        }.bind(null, key));
        /******/
        return ns;
        /******/
    };
    /******/
    /******/ 	// getDefaultExport function for compatibility with non-harmony modules
    /******/
    __webpack_require__.n = function (module) {
        /******/
        var getter = module && module.__esModule ?
            /******/            function getDefault() {
                return module['default'];
            } :
            /******/            function getModuleExports() {
                return module;
            };
        /******/
        __webpack_require__.d(getter, 'a', getter);
        /******/
        return getter;
        /******/
    };
    /******/
    /******/ 	// Object.prototype.hasOwnProperty.call
    /******/
    __webpack_require__.o = function (object, property) {
        return Object.prototype.hasOwnProperty.call(object, property);
    };
    /******/
    /******/ 	// __webpack_public_path__
    /******/
    __webpack_require__.p = "";
    /******/
    /******/
    /******/ 	// Load entry module and return exports
    /******/
    return __webpack_require__(__webpack_require__.s = 12);
    /******/
})
    /************************************************************************/
    /******/ ({

    /***/ "./app/assets/es6/pages/form-validation.js":
    /*!*************************************************!*\
      !*** ./app/assets/es6/pages/form-validation.js ***!
      \*************************************************/
    /*! no static exports found */
    /***/ (function (module, exports) {

        eval("class FormValidation {\r\n\r\n    static init() {\r\n\r\n        $( \"#form-validation\" ).validate({\r\n            ignore: ':hidden:not(:checkbox)',\r\n            errorElement: 'div',\r\n            errorClass: 'is-invalid',\r\n            validClass: 'is-valid',\r\n            rules: {\r\n                productname: {\r\n                    required: true\r\n                },\r\n type: {\r\n                    required: true\r\n                },\r\n    sizeProduct: {\r\n                    required: true\r\n                },\r\n     colorProduct: {\r\n                    required: true\r\n                },\r\n      sale: {\r\n                    min: 0\r\n                },\r\n       thumbnail: {\r\n                    required: true\r\n                },\r\n   images: {\r\n                    required: true\r\n                },\r\n        description: {\r\n                    required: true\r\n                },\r\n     fullname: {\r\n                    required: true\r\n                },\r\n     username: {\r\n                    required: true\r\n                },\r\n     birth: {\r\n                    required: true\r\n                },\r\n     position: {\r\n                    required: true\r\n                },\r\n     image: {\r\n                    required: true\r\n                },\r\n     facebook: {\r\n                    required: true\r\n                },\r\n     phone: {\r\n                    required: true,\r\n                    digits: true,\r\n    phoneRegex: true\r\n            },\r\n      inputMinLength: {\r\n                    required: true,\r\n                    minlength: 6\r\n                },\r\n                inputMaxLength: {\r\n                    required: true,\r\n                    minlength: 8\r\n                }, \r\n                inputUrl: {\r\n                    required: true,\r\n                    url: true\r\n                },\r\n                inputRangeLength: {\r\n                    required: true,\r\n                    rangelength: [2, 6]\r\n                },\r\n                inputMinValue: {\r\n                    required: true,\r\n                    min: 8\r\n                },\r\n                inputMaxValue: {\r\n                    required: true,\r\n                    max: 6\r\n                },\r\n                inputRangeValue: {\r\n                    required: true,\r\n                    max: 6,\r\n                    range: [6, 12]\r\n                },\r\n                email: {\r\n                    required: true,\r\n                    email: true\r\n                },\r\n                password: {\r\n                    required: true,\r\n      passwordRegex: true\r\n           },\r\n                inputPasswordConfirm: {\r\n                    required: true,\r\n                    equalTo: '#password'\r\n                },\r\n                inputDigit: {\r\n                    required: true,\r\n                    digits: true\r\n                },\r\n                inputValidCheckbox: {\r\n                    required: true\r\n                }\r\n            }\r\n        });\r\n    }\r\n}\r\n\r\n$(() => { FormValidation.init(); });\r\n\r\n\n\n//# sourceURL=webpack:///./app/assets/es6/pages/form-validation.js?");

        /***/
    }),

    /***/ 12:
    /*!*******************************************************!*\
      !*** multi ./app/assets/es6/pages/form-validation.js ***!
      \*******************************************************/
    /*! no static exports found */
    /***/ (function (module, exports, __webpack_require__) {

        eval("module.exports = __webpack_require__(/*! C:\\Users\\Nate\\Desktop\\themeforest selling\\Enlink-bootstrap\\v1.0.0\\Enlink - Bootstrap Admin Template\\demo\\app\\assets\\es6\\pages\\form-validation.js */\"./app/assets/es6/pages/form-validation.js\");\n\n\n//# sourceURL=webpack:///multi_./app/assets/es6/pages/form-validation.js?");

        /***/
    })

    /******/
});

