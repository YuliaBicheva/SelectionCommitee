jQuery(function( $ ){
    //
    // $.localScroll({
    //     duration: 750
    // });

    if( $( document ).scrollTop() > 0 ){
        $( '.site-header' ).addClass( 'shrink' );
    }

    // Add opacity class to site header
    $( document ).on('scroll', function(){

        if ( $( document ).scrollTop() > 0 ){
            $( '.site-header' ).addClass( 'shrink' );

        } else {
            $( '.site-header' ).removeClass( 'shrink' );
        }

    });
    
});

