<?php 

    error_reporting(E_ALL); 
    ini_set('display_errors',1); 

    include('dbcon.php');

    $android = strpos($_SERVER['HTTP_USER_AGENT'], "Android");

    if( (($_SERVER['REQUEST_METHOD'] == 'POST') && isset($_POST['submit'])) || $android )
    {


        $name=$_POST['name'];
        $review=$_POST['review'];

        if(empty($name)){
            $errMSG = "가게 이름을 입력하세요.";
        }

        if(!isset($errMSG))
        {
            try{
                $stmt = $con->prepare('INSERT INTO visit(name, review) VALUES(:name, :review)');
                $stmt->bindParam(':name', $name);
                $stmt->bindParam(':review', $review);

                if($stmt->execute())
                {
                    $successMSG = "리뷰를 추가했습니다.";
                }
                else
                {
                    $errMSG = "리뷰 추가 에러";
                }

            } catch(PDOException $e) {
                die("Database error: " . $e->getMessage()); 
            }
        }

    }

?>


<?php 
    if (isset($errMSG)) echo $errMSG;
    if (isset($successMSG)) echo $successMSG;

	$android = strpos($_SERVER['HTTP_USER_AGENT'], "Android");
   
    if( !$android )
    {
?>
    <html>
       <body>

            <form action="<?php $_PHP_SELF ?>" method="POST">
                Name: <input type = "text" name = "name" />
                Review: <input type = "text" name = "review" />
                <input type = "submit" name = "submit" />
            </form>
       
       </body>
    </html>

<?php 
    }
?>