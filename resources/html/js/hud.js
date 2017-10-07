/**
 * contains the functions needed to add an icon into their holders
 * @author Andrew McGhie
 */

function addUnitIcon(image, unit) {
  var icon = $(
      '<div '
      + 'class="icon" '
      + 'onclick="controller.unitIconBtn(unit)">'
      + '</div>');
  icon.css('background-image', 'url("' + image + '")');
  $('#unit-holder').append(icon);
}

function addAbilityIcon(image, ability) {
  var icon = $(
      '<div '
      + 'class="icon" '
      + 'onclick="controller.abilityIconBtn(ability)">'
      + '</div>');
  icon.css('background-image', 'url("' + image + '")');
  $('#ability-holder').append(icon);
}

function addItemIcon(image, item) {
  var icon = $(
      '<div '
      + 'class="icon" '
      + 'onclick="controller.itemIconBtn(item)">'
      + '</div>');
  icon.css('background-image', 'url("' + image + '")');
  $('#item-holder').append(icon);
}

function clearUnits() {
  $('#unit-holder').html('');
}

function clearAbilties() {
  $('#ability-holder').html('');
}

function clearItems() {
  $('#item-holder').html('');
}

// (function() {
    var gameViewProxy = $('#game-view-proxy');
    var menuButton = $('#menu-button');
    var resumeButton = $('#resume-btn');


    gameViewProxy.on('click', function (event) {
        controller.onLeftClick(event.pageX, event.pageY, event.shiftKey, event.ctrlKey);
    });
    gameViewProxy.on('contextmenu', function (event) {

        controller.onRightClick(event.pageX, event.pageY, event.shiftKey, event.ctrlKey);
        return false;
    });

    menuButton.on('click', function (event) {
        $('#overlay').fadeIn();
        $('#pause-menu').fadeIn();
    });

    resumeButton.on('click', function (event) {
        $('#overlay').fadeOut();
        $('#pause-menu').fadeOut();
    });
// })
