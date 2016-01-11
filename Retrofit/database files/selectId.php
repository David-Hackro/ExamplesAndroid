<?php
//Obtener todos los anunciosz
error_reporting(0);
include("db_config.php");

$response = array();

  
    $id_usuario=$_POST['id_usuario'];

    $result = mysql_query("select * from Persona where id_usuario = ".$id_usuario."");

     echo json_encode(mysql_fetch_assoc($result));

?>