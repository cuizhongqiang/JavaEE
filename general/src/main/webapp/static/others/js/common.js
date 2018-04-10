function cumSpace(count) {
	var str = "&nbsp;";
	for (var i = 0; i < count; i++) {
		str += "&nbsp;";
	}
	return str;
}

Array.prototype.contains = function(obj) {
	var x = this.length;
	while (x--) {
		if (this[x] == obj) {
			return true;
		}
	}
	return false;
}