
<?php
require './PHPMailerAutoload.php';
 
    $emisor=$_POST['emisor'];
    $receptor=$_POST['receptor'];
    $asunto=$_POST['asunto'];
    $mensaje=$_POST['mensaje'];

 

$mail = new PHPMailer;

//$mail->SMTPDebug = 3;                               // Enable verbose debug output

$mail->isSMTP();                                      // Set mailer to use SMTP
$mail->Host = 'smtp.gmail.com';  // Specify main and backup SMTP servers
$mail->SMTPAuth = true;                               // Enable SMTP authentication
$mail->Username = $emisor;               			  // SMTP username
$mail->Password = 'TU PASS';                           // SMTP password
$mail->SMTPSecure = 'tls';                            // Enable TLS encryption, `ssl` also accepted
$mail->Port = 587;                                    // TCP port to connect to

$mail->From = $emisor;
$mail->FromName = $emisor;
$mail->addAddress($receptor);    

//$mail->isHTML(true);                                  // Set email format to HTML
$mail->Subject = $asunto;
//$mail->Body    = 'This is the HTML message body <b>in bold!</b>';
$mail->Body = $mensaje;

$mail->AltBody = 'This is the body in plain text for non-HTML mail clients';

if(!$mail->send()) {
    echo 'Message could not be sent.';
    echo 'Mailer Error: ' . $mail->ErrorInfo;
} else {
    echo 'Message has been sent';
}
	
?>s