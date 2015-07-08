/*
 * AngularJS kontrollerid
 * 
 */

function DataListController($scope, $http) {

	$scope.init = function(componentId, displayId) {
		$scope.componentId = componentId;
		$scope.displayId = displayId;
		$scope.targetComp = convertDisplayIdToPath(displayId);
	};

	$scope.sort = function(orderedField, order) {
		var params = {};
		params[$scope.targetComp + ".pagedListHolder.order.orderedField"] = orderedField;
		params[$scope.targetComp + ".pagedListHolder.order.desc"] = order;
		$scope.send(params, "submitListTableSort");
	};

	$scope.pagination = function(pageNr) {
		var params = {};
		params[$scope.targetComp + ".pagedListHolder.pagination.pageNr"] = pageNr;
		$scope.send(params, "submitListTablePagination");
	};
	
	$scope.showAll = function(show) {
		var params = {};
		params[$scope.targetComp + ".properties.showAll"] = show;
		$scope.send(params, "submitListTablePagination");
	};

	$scope.filter = function() {
		var params = {};
		var thead = getJQueryElementById($scope.displayId).find("thead");
		$.each(thead.find('input, textarea, select').serializeArray(),
				function(i, field) {
					params[field.name] = field.value;
				});

		$scope.send(params, "submitListTableFilter");
	};

	$scope.deleteRow = function(event, eventName, targetComp, param) {
		if ($(event.currentTarget).data("hasConfirmation")) {
			var params = {};
			injectAjaxEventParams(params, eventName, targetComp, "");
			sendAjaxRequest(params, function(response) {
				showConfirmationModalDialog(response.data);
			});
		} else {
			event.target.parentNode.parentNode.style.display = 'none';
			return $scope.sendSubmit(event.target, eventName, targetComp);
		}
	};

	$scope.editRow = function(event, eventName, targetComp) {
		return $scope.sendSubmit(event.target, eventName, targetComp);
	};

	$scope.addRow = function(event, targetComp) {
		var path = convertDisplayIdToPath($scope.displayId);
		addHiddenField(path + ".addRowSubmit", true);
		return $scope.sendSubmit(event.target, "submitListTableAddRow", targetComp);
	};

	$scope.sendSubmit = function(event, eventName, targetComp) {
		addHiddenField("submitListComponent", $scope.componentId);
		return epmEventSubmit(event.target, eventName, targetComp);
	};

	$scope.send = function(params, evt) {
		
		addFormDataToHolder(params);
		
		params["submitListComponent"] = $scope.componentId;
		injectAjaxEventParams(params, evt, $scope.targetComp, "");

		angularSendPostRequest($http, params, function(responseElement) {
			
			var el = findChildById(responseElement, $scope.displayId);
			$scope.listData = el.innerHTML;
			$scope.listPaginationData = "&nbsp;";
			
			var paginationEl = getJQueryElementById($scope.displayId + ".pagination");
			if (paginationEl) {
				paginationEl = $(paginationEl);
				el = findChildById(responseElement, $scope.displayId + ".pagination");
				if (el) {
					$scope.listPaginationData = el.innerHTML;
				}
			}
		});
	};
}

function UpdateRegionController($scope, $http) {
	$scope.init = function(componentId, displayId) {
		$scope.componentId = componentId;
		$scope.displayId = displayId;
		$scope.targetComp = convertDisplayIdToPath(displayId);
	};

	$scope.sendSubmit = function(event, eventName, targetComp) {
		addHiddenField("submitListComponent", $scope.componentId);
		return epmEventSubmit(event.target, eventName, targetComp);
	};
	
	$scope.send = function(event, eventName, targetComp, region) {
//		params["submitListComponent"] = $scope.componentId;
		addHiddenField(param_epm_evt, eventName);
		addHiddenField(param_epm_evt_target, targetComp);
		addHiddenField(param_epm_evt_region, parameter);
		injectAjaxEventParams(params, evt, $scope.targetComp, "");

		angularSendPostRequest($http, params, function(responseElement) {
			var el = findChildById(responseElement, region);
			var mainEl = getJQueryElementById(region);
			mainEl.html(el.innerHTML);
			angular.bootstrap(mainEl, [ ANGULAR_MODULE_NAME ]);

			//TODO: see võiks olla mingi üks üldine funktsioon, mis siis teeb kõik vajalikud asjad
			addDateFieldMask(mainEl);
			addElementEnterHandling(mainEl);
			setDatepicks(mainEl);
		});
	};

}

function angularSendPostRequest($http, params, callback) {

	startLoader();
	
	var transform = function(data) {
		return $.param(data);
	};
	
	$http.post(window.location.href, params, {
			headers : {
			'Content-Type' : 'application/x-www-form-urlencoded; charset=UTF-8',
			'Accept' : 'text/plain'
			},
			transformRequest : transform
		}).success(function(responseData) {
				var data = $.parseHTML(responseData);
				
				var childs = [];
				findChildsByClass(data, "messages", childs);
				for (var rMessage in childs) {
					var lMessage = $(document).find("#"+childs[rMessage].id);
					lMessage.html(childs[rMessage].innerHTML);
				}
				callback(data);
				closeLoader();
			}).error(function(data, status, headers, config) {
				closeLoader();
				if (data && data.messages) {
					showMessages(data);
				} else {
					alert("Esines viga! Staatus: " + status);
				}
			});
}