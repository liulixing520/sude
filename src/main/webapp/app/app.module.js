(function() {
    'use strict';

    angular
        .module('sudeApp', [
            'ngStorage', 
            'tmh.dynamicLocale',
            'pascalprecht.translate', 
            'ngResource',
            'ngCookies',
            'ngAria',
            'ngCacheBuster',
            'ui.bootstrap',
            'ui.bootstrap.datetimepicker',
            'ui.router',
            // jhipster-needle-angularjs-add-module JHipster will add new module here
            'angular-loading-bar'
        ])
        .run(run);

    run.$inject = ['stateHandler', 'translationHandler'];

    function run(stateHandler, translationHandler) {
        stateHandler.initialize();
        translationHandler.initialize();
        
        var public_vars = public_vars || {};
        // Define Public Vars
		public_vars.$body = jQuery("body");
        // Page Loading Overlay
    	public_vars.$pageLoadingOverlay = jQuery('.page-loading-overlay');

    	jQuery(window).load(function()
    	{
    		public_vars.$pageLoadingOverlay.addClass('loaded');
    	})
    }
})();
