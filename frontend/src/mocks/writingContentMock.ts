export const writingContentMock = `
<h1>테스트 h1 abcdef 123</h1>
<h2>테스트 h2 abcdef 123</h2>
<h3>테스트 h3 abcdef 123</h3>
<h4>테스트 h4 abcdef 123</h4>
<h5>테스트 h5 abcdef 123</h5>
<h6>테스트 h6 abcdef 123</h6>
<p> This is some basic, sample markdown. 이것은 샘플 이것은 샘플</p>
<ul>
	<li>
		li
		<ol>
			<li>리스트 아이템 테스트</li>
			<li>아이템 리스트 테스트</li>
			<li>뷁뤡웱쿍</li>
		</ol>
	</li>
	<li>
		<p>More</p>
		<blockquote>
		<p>좋아좋아좋아좋아좋아좋아좋아좋아좋아좋아좋아좋아좋아좋아좋아좋아좋아좋아좋아좋아좋아좋아좋아좋아좋아좋아좋아좋아좋아좋아좋아좋아좋아</p>
        <p><code>좋네</code>요</p>
        <p>EZ Clap</p>
		</blockquote>
        <a href="https://www.naver.com">네이버 링크</a>
		<p>And <strong>bold</strong>, <em>italics</em>, and even <em>italics and later <strong>bold</strong></em>. Even <del>strikethrough</del>. <a href="https://www.ttmkt.com">A link</a> to somewhere.</p>
		<p>And code highlighting:</p>
		<pre><code class="language-javascript">
function foo () {
    console.log('hello, world!');
} 
		</code></pre>
        <br/>
				<h2>hello <strong>every</strong>one!</h2>
				<ul>
					<li>today</li>
					<li>
						we will
						gonna..alksdjhfals<strong>kdhf</strong>laskjdhflkajshdflkashflkjahsflkhasldfjhaslkdfhlkasdhflkashdlfkhaslkdfhlakshflkahsdjlkfhlaksdhflaskldfhlkasdhflahsdkjfhlakjsdhflkashklfhask<em>jldhf</em>lashdfhalskdhfklahsdflhaslkdfhlajhdflkajshdflkjashdflkashdfljhaslkdfhalksdhflkahdflkhasdlkjfhlakj
					</li>
				</ul>
				<h3>bye <code>everyon</code>e~~</h3>
				<pre><code class="language-java">@Entity\n@Getter\n@NoArgsConstructor(access = AccessLevel.PROTECTED)\npublic class Block {\n    @Id\n    @GeneratedValue(strategy = GenerationType.IDENTITY)\n    private Long id;\n    @ManyToOne(fetch = FetchType.LAZY)\n    @JoinColumn(name = \"writing_id\")\n    private Writing writing;\n    @OneToOne(fetch = FetchType.LAZY)\n    @JoinColumn(name = \"content_id\")\n    private Content content;\n\n    public Block(final Writing writing, final Content content) {\n        this(null, writing, content);\n    }\n\n    public Block(final Long id, final Writing writing, final Content content) {\n        this.id = id;\n        this.writing = writing;\n        this.content = content;\n    }\n}\n</code></pre>
				<blockquote>this is from hubcreator</blockquote>
				
				<br/>
		<pre><code class="language-c">
int main()
{
    char *str = malloc(10);
    for (int i = 0; i < 10; i++)
    {
        str[i] += 97 + i;
    }
}
		</code></pre>
		<p>Or inline code like <code>var foo = &#39;bar&#39;;</code>.</p>
		<p>Or an image of bears</p>
		<p><img src="https://placebear.com/200/200" alt="bears"></p>
		<p>The end ...</p>
	</li>
</ul>`;
