// These constants are injected via webpack DefinePlugin variables.
// You can add more variables in webpack.common.js or in profile specific webpack.<dev|prod>.js files.
// If you change the values in the webpack config files, you need to re run webpack to update the application

declare const __DEBUG_INFO_ENABLED__: boolean;
declare const __VERSION__: string;
declare const __SITE_KEY_CAPTCHA__: string;
declare const __TIRILLA_URI__: string;

export const TIRILLA_URI = __TIRILLA_URI__;
export const SITE_KEY_CAPTCHA = __SITE_KEY_CAPTCHA__;
export const VERSION = __VERSION__;
export const DEBUG_INFO_ENABLED = __DEBUG_INFO_ENABLED__;
