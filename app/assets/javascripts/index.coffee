$ ->
  $.get "/users", (users) ->
    $.each users, (index, user) ->
      $("#users").append("<li>
		<form method='POST' action='/delUser'>
			<button>X</button>
			<input style='display:none' name=id value='"+user.id+"'>
			<input style='display:none' name=name value='"+user.name+"'>
			"+user.name+"
		</form>
		</li>")