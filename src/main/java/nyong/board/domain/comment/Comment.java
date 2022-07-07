package nyong.board.domain.comment;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nyong.board.domain.BaseTimeEntity;
import nyong.board.domain.member.Member;
import nyong.board.domain.post.Post;

import javax.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static javax.persistence.FetchType.LAZY;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Comment extends BaseTimeEntity {

    @Id @GeneratedValue
    @Column(name = "comment_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "writer_id")
    private Member writer;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "parent_id")
    private Comment parent;

    @Lob
    @Column(nullable = false)
    private String content;

    private boolean isRemoved = false;


    //== 부모 댓글을 삭제해도 자식 댓글은 남아있음 ==//
    @OneToMany(mappedBy = "parent")
    private List<Comment> childList = new ArrayList<>();


    //== 연관관계 편의 메서드 ==//
    public void confirmWriter(Member writer) {
        this.writer = writer;
        writer.addComment(this);
    }

    public void confirmPost(Post post) {
        this.post = post;
        post.addComment(this);
    }

    public void confirmParent(Comment parent) {
        this.parent = parent;
        parent.addChild(this);
    }

    public void addChild(Comment child) {
        childList.add(child);
    }


    //== 수정 ==//
    public void updateContent(String content) {
        this.content = content;
    }

    //== 삭제 ==//
    public void remove() {
        this.isRemoved = true;
    }

    // 삭제할 댓글 List
    public List<Comment> findRemovableList() {

        List<Comment> result = new ArrayList<>();

        // 부모 댓글이 있는지 없는지 확실하지 않음
        Optional.ofNullable(this.parent).ifPresentOrElse(

                parentComment -> { // 대댓글인 경우 - 안지워졌으면 false(default : false)

                    // 부모 댓글이 안지워졌으며 대댓글들이 모두 지워지거나(true) 안지워졌다면(false)
                    if(parentComment.isRemoved() && parentComment.isAllChildRemoved()){

                        // 모든 대댓글을 list 에 저장
                        result.addAll(parentComment.getChildList());

                        // 마지막에 부모 댓글 저장
                        result.add(parentComment);
                    }
                },

                () -> { // 댓글인 경우
                    if (isAllChildRemoved()) {
                        result.add(this);
                        result.addAll(this.getChildList());
                    }
                }
        );

        return result;
    }

    //모든 자식 댓글이 삭제되었는지 판단
    private boolean isAllChildRemoved() {
        return getChildList().stream()
                .map(Comment::isRemoved) // 삭제 여부
                .filter(isRemove -> !isRemove) // 지워졌으면 true, 안지워졌으면 false
                .findAny() // 지워지지 않은것이 하나라도 있다면 false를 반환
                .orElse(true); // 모두 지워졌다면 true를 반환

    }

    @Builder
    public Comment(Member writer, Post post, Comment parent, String content) {
        this.writer = writer;
        this.post = post;
        this.parent = parent;
        this.content = content;
        this.isRemoved = false;
    }

}
