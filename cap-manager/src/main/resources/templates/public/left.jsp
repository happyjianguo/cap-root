<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<div class="col-sm-2 col-lg-2">
	<div class="sidebar-nav">
		<div class="nav-canvas">
			<div class="nav-sm nav nav-stacked">
			</div>
			<ul class="nav nav-pills nav-stacked main-menu">
   				<li class="nav-header">管理系统	</li>
   				<!-- 
				<li><a class="ajax-link" href="index.html"><i class="glyphicon glyphicon-home"></i><span> Dashboard</span></a></li>
				<li><a class="ajax-link" href="ui.html"><i class="glyphicon glyphicon-eye-open"></i><span> UI Features</span></a></li>
				<li><a class="ajax-link" href="form.html"><i class="glyphicon glyphicon-edit"></i><span> Forms</span></a></li>
				<li><a class="ajax-link" href="chart.html"><i class="glyphicon glyphicon-list-alt"></i><span> Charts</span></a></li>
				<li><a class="ajax-link" href="typography.html"><i class="glyphicon glyphicon-font"></i><span> Typography</span></a></li>
				<li><a class="ajax-link" href="gallery.html"><i class="glyphicon glyphicon-picture"></i><span> Gallery</span></a></li>
				<li class="nav-header hidden-md">Sample Section</li>
				<li><a class="ajax-link" href="table.html"><i class="glyphicon glyphicon-align-justify"></i><span> Tables</span></a></li>
   				 -->
				
				<li class="accordion"><a href="#"><i class="glyphicon glyphicon-plus"></i><span>用户管理</span></a>
					<ul class="nav nav-pills nav-stacked">
						<li><a href="${pageContext.request.contextPath }/user/toadd.html">新增用户</a></li>
						<li><a href="${pageContext.request.contextPath }/bootstrap/boot.do">普通用户管理</a></li>
						<li><a href="${pageContext.request.contextPath }/bootstrap/boot.do">特权用户管理</a></li>
						<li><a href="${pageContext.request.contextPath }/bootstrap/boot.do">用户角色管理</a></li>
					</ul>
				</li>
				<!-- 
					<li><a class="ajax-link" href="calendar.html"><i class="glyphicon glyphicon-calendar"></i><span> Calendar</span></a></li>
					<li><a class="ajax-link" href="grid.html"><i class="glyphicon glyphicon-th"></i><span> Grid</span></a></li>
					<li><a href="tour.html"><i class="glyphicon glyphicon-globe"></i><span> Tour</span></a></li>
					<li><a class="ajax-link" href="icon.html"><i class="glyphicon glyphicon-star"></i><span> Icons</span></a></li>
					<li><a href="error.html"><i class="glyphicon glyphicon-ban-circle"></i><span> Error Page</span></a></li>
					<li><a href="login.html"><i class="glyphicon glyphicon-lock"></i><span> Login Page</span></a></li>
				 -->
			</ul>
			<!-- 
				<label id="for-is-ajax" for="is-ajax"><input id="is-ajax" type="checkbox"> Ajax on menu</label>
			-->
		</div>
	</div>
</div>
