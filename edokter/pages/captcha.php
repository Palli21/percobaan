<?php
session_start();

header("Content-type: image/png");
header("Cache-Control: no-store, no-cache, must-revalidate, max-age=0");
header("Pragma: no-cache");

$captcha = '';
for ($i = 0; $i < 6; $i++) {
    $captcha .= (string) rand(0, 9);
}

$_SESSION["Capcay"] = $captcha;

$width = 200;
$height = 50;
$image = imagecreate($width, $height);

$background = imagecolorallocate($image, 30, 144, 255);
$text = imagecolorallocate($image, 253, 252, 252);
$noise = imagecolorallocate($image, 180, 220, 255);

imagefill($image, 0, 0, $background);

for ($i = 0; $i < 80; $i++) {
    imagesetpixel($image, rand(0, $width - 1), rand(0, $height - 1), $noise);
}

for ($i = 0; $i < 6; $i++) {
    imageline(
        $image,
        rand(0, $width - 1),
        rand(0, $height - 1),
        rand(0, $width - 1),
        rand(0, $height - 1),
        $noise
    );
}

imagerectangle($image, 0, 0, $width - 1, $height - 1, $noise);
imagestring($image, 5, 32, 17, $captcha, $text);

imagepng($image);
imagedestroy($image);
?>
